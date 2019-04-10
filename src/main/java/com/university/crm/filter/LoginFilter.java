package com.university.crm.filter;


import com.university.crm.constant.Constant;
import com.university.crm.context.RequestThreadContext;
import com.university.crm.util.CookieKit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * date: 2019/03/17
 * description :登录过滤器
 *
 * @author : zhencai.cheng
 */
public class LoginFilter extends AbstractFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String account = checkLogin((HttpServletRequest) request);

        RequestThreadContext.setTraceID(request.getParameter("traceID"));
        if (account != null) {
            request.setAttribute("account", account);
            RequestThreadContext.setAccount(account);
            filterChain.doFilter(request, response);
        } else {
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
        if (authCookieValue == null) {
            authCookieValue = request.getHeader(Constant.authCookieName);
        }
        return getAccount(authCookieValue);
    }

    @Override
    public void destroy() {
    }
}
