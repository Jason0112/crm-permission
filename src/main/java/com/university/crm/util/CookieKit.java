package com.university.crm.util;

import com.university.crm.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * date: 2017/8/6
 * description : cookie工具类
 *
 * @author : zhencai.cheng
 */
public class CookieKit {

    /**
     * 更新cookie
     *
     * @param name   cookie名称
     * @param value  cookie值
     * @param path   cookie存放路径
     * @param domain cookie域
     * @param maxAge cookie最长时间
     */
    public static void updateCookie(String name, String value, String path, String domain, int maxAge) throws BusinessException {
        Cookie cookie = new Cookie(name, value);
        if(StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        HttpServletResponse response = RequestKit.getResponse();
        response.addCookie(cookie);
    }


    /**
     * 更新cookie
     *
     * @param response response
     * @param cookie   cookie
     */
    public static void updateCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }


    /**
     * 获取cookie
     *
     * @param request request
     * @param name    cookie的名称
     * @return cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        return cookieMap.getOrDefault(name, null);
    }

    /**
     * 获取cookie的值
     *
     * @param request request
     * @param name    cookie的名称
     * @return cookie值
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name).getValue();
        } else {
            return null;
        }
    }

    /**
     * 获取cookie转成map
     *
     * @param request request
     * @return map
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}