package com.university.crm.context;

/**
 * date: 2019/3/17
 * description : 请求线程上下文-本地线程变量
 *
 * @author : zhencai.cheng
 */
public class RequestThreadContext {

    //请求缀
    private final static ThreadLocal<String> TRACE_ID = new ThreadLocal<>();
    //登录账号
    private final static ThreadLocal<String> ACCOUNT = new ThreadLocal<>();


    public static String getTraceID() {
        return TRACE_ID.get();
    }

    public static void setTraceID(String traceID) {
        TRACE_ID.set(traceID);
    }

    public static String getAccount() {
        return ACCOUNT.get();
    }

    public static void setAccount(String traceID) {
        ACCOUNT.set(traceID);
    }
}
