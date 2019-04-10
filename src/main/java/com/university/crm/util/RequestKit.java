package com.university.crm.util;

import com.alibaba.fastjson.JSONObject;
import com.university.crm.exception.BusinessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;


/**
 * date: 2019/03/17
 * description : 请求工具类
 *
 * @author : zhencai.cheng
 */
public class RequestKit {
    /**
     * 获取远程访问IP地址
     *
     * @return IP
     */
    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            String[] ipArray = ip.split(",");
            if (ipArray.length > 1) {
                ip = ipArray[0];
            }
        }
        return ip;
    }

    public static HttpServletResponse getResponse() throws BusinessException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        throw new BusinessException("", true);
    }

    public static HttpServletRequest getRequest() throws BusinessException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        throw new BusinessException("", true);
    }

    public static JSONObject getRequestParameterMap() throws BusinessException {
        HttpServletRequest request = getRequest();
        return getRequestAllParameterMap(request);
    }

    /**
     * 获取请求参数,同名参数只打印第一个
     *
     * @param request request
     * @return 请求参数
     */
    public static JSONObject getRequestParameterMap(HttpServletRequest request) {
        Map properties = request.getParameterMap();
        Iterator it = properties.entrySet().iterator();
        JSONObject paramMap = MapKit.newJSONObject(properties.size());
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Object objectValue = entry.getValue();
            String value;
            if (null == objectValue) {
                value = "";
            } else if (objectValue instanceof String[]) {
                String[] values = (String[]) objectValue; //只打印同名参数的第一个值
                value = values[0];
            } else {
                value = objectValue.toString();
            }
            paramMap.put(key, value);
        }
        return paramMap;
    }

    /**
     * 获取所有请求参数,同名参数的值用-拼接
     *
     * @param request request
     * @return 所有请求参数
     */
    public static JSONObject getRequestAllParameterMap(HttpServletRequest request) {
        Map properties = request.getParameterMap();
        Iterator it = properties.entrySet().iterator();
        JSONObject paramMap = MapKit.newJSONObject(properties.size());
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Object objectValue = entry.getValue();
            String value = "";
            if (null == objectValue) {
                value = "";
            } else if (objectValue instanceof String[]) {
                String[] values = (String[]) objectValue;
                int valuesLength = values.length;
                for (int i = 0; i < valuesLength; i++) {  //同名参数的值用-拼接
                    if (i == (valuesLength - 1)) {
                        value = value + values[i];
                    } else {
                        value = value + values[i] + "-";
                    }
                }
            } else {
                value = objectValue.toString();
            }
            paramMap.put(key, value);
        }
        return paramMap;
    }

    /**
     * 获取请求数据
     *
     * @param request request
     * @return 请求数据
     */
    public static String getRequestData(HttpServletRequest request) {
        StringBuilder requestData = new StringBuilder();
        requestData.append("requestIP:").append(getRequestIP(request));
        requestData.append("--requestURL:").append(request.getRequestURL());
        requestData.append("--referer:").append(request.getHeader("referer"));
        requestData.append("--allParameterMap:").append(getRequestAllParameterMap(request));
        return requestData.toString();
    }
}