package com.university.crm.interceptor.ip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.university.crm.bean.base.ResponseJson;
import com.university.crm.constant.ResponseCode;
import com.university.crm.util.RequestKit;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class IPValidInterceptor extends HandlerInterceptorAdapter {

    private boolean openIPCheck = true;
    private String accessIP;
    private ConcurrentMap<String, Boolean> cache = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (openIPCheck) {
            String IP = RequestKit.getRequestIP(request);
            if (IP != null && isAllow(IP)) {
                return true;
            } else {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                ResponseJson result = new ResponseJson();
                result.setTraceID(request.getParameter("traceID"));
                result.setCode(ResponseCode.REQUEST_ERROR_REFUSE_IP);
                result.setMsg("调用方IP:[" + IP + "]不在允许访问列表");
                result.setData(new JSONObject());

                out.print(JSON.toJSONString(result));
                out.flush();
                out.close();
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isAllow(String ip) {
        //如果已经通过验证,直接返回
        if (cache.containsKey(ip)) {
            return true;
        } else {
            //遍历白名单,判断是否匹配
            String[] accessIPArray = accessIP.split("\\|");
            for (String tarIP : accessIPArray) {
                String[] srcIPGroup = ip.split("\\.");
                String[] tarIPGroup = tarIP.split("\\.");
                boolean match = true;
                for (int i = 0; i < 4; i++) {//分组计算
                    if (tarIPGroup[i].contains("-")) {//区间通配符判断是否在区间内
                        String[] tmp = tarIPGroup[i].replaceAll("[\\[\\]]", "").split("\\-");
                        int min = Integer.valueOf(tmp[0]);
                        int max = Integer.valueOf(tmp[1]);
                        int srcTmp = Integer.valueOf(srcIPGroup[i]);
                        if (srcTmp < min || srcTmp > max) {
                            match = false;
                            break;
                        }
                    } else if (!"*".equals(tarIPGroup[i]) && !srcIPGroup[i].equals(tarIPGroup[i])) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    cache.put(ip, true);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isOpenIPCheck() {
        return openIPCheck;
    }

    public void setOpenIPCheck(boolean openIPCheck) {
        this.openIPCheck = openIPCheck;
    }

    public String getAccessIP() {
        return accessIP;
    }

    public void setAccessIP(String accessIP) {
        this.accessIP = accessIP;
    }
}