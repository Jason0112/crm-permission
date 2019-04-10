package com.university.crm.filter;


import com.university.crm.util.RequestKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * date: 2019/03/17
 * description :请求过滤器
 *
 * @author : zhencai.cheng
 */
public class RequestFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);
    private static Set<String> EXCLUDE_URL = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration<String> enumeration = filterConfig.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            EXCLUDE_URL.add(filterConfig.getInitParameter(enumeration.nextElement()));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        long beginTime = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        boolean print = true;
        if (EXCLUDE_URL.contains(requestURI) || !(requestURI.contains(".json") || requestURI.contains(".action"))) {
            print = false;
        }
        //记录请求参数
        if (LOGGER.isInfoEnabled() && print) {
            LOGGER.info("requestURI:[{}], requestData:[{}]", requestURI, RequestKit.getRequestData(request));
        }

        chain.doFilter(servletRequest, servletResponse);
        //记录请求执行时长
        if (LOGGER.isInfoEnabled() && print) {
            LOGGER.info("requestURI:[{}], requestExecuteTime:[{}]ms", requestURI, (System.currentTimeMillis() - beginTime));
        }
    }

    @Override
    public void destroy() {

    }
}
