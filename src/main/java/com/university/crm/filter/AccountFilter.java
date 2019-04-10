package com.university.crm.filter;

import com.university.crm.constant.Constant;
import com.university.crm.context.RequestThreadContext;
import com.university.crm.service.CommonService;
import com.university.crm.util.ApplicationContextUtil;
import com.university.crm.util.CookieKit;
import com.university.crm.util.RequestKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * date: 2019/03/17
 * description :账号权限过滤器
 *
 * @author : zhencai.cheng
 */
public class AccountFilter extends AbstractFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AccountFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //1.检查是否登陆
        String account = checkLogin((HttpServletRequest) request);
        if (account != null) {
            request.setAttribute("account", account);
            RequestThreadContext.setAccount(account);
            //2.检查是否有权限
            if (checkPermission((HttpServletRequest) request, account)) {
                filterChain.doFilter(request, response);
            } else {
                //无权限调转到无权限提示页面，也可以自己定制页面
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("/noPermission.action");
            }
        } else {
            //没有登陆调转到登陆页面
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(Constant.loginURL);
        }
    }

    /**
     * 检测登陆状态
     *
     * @param request request
     * @return 返回登陆帐号
     */
    private String checkLogin(HttpServletRequest request) {
        String authCookieValue = CookieKit.getCookieValueByName(request, Constant.authCookieName);
        return getAccount(authCookieValue);
    }

    /**
     * 检查帐号是否拥有权限
     *
     * @param request request
     * @param account 帐号
     * @return 是否拥有权限
     */
    private boolean checkPermission(HttpServletRequest request, String account) {
        try {
            request.setCharacterEncoding("UTF-8");
            String url = request.getRequestURL().toString();
            if (url.contains(";jsessionid")) {
                url = url.substring(0, url.indexOf(";jsessionid"));
            }
            Map<String, String> businessParams = new HashMap<>();
            businessParams.put("account", account);
            businessParams.put("permissionSign", url);
            businessParams.put("operatorIP", RequestKit.getRequestIP(request));
            return ApplicationContextUtil.getBean(CommonService.class).checkPermission(businessParams);
        } catch (Exception e) {
            logger.error("{}",e);
        }
        return false;
    }

    @Override
    public void destroy() {
    }

}
