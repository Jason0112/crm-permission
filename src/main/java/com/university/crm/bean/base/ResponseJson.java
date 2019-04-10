package com.university.crm.bean.base;

import com.university.crm.constant.ResponseCode;
import com.university.crm.context.RequestThreadContext;

/**
 * date: 2019/3/17
 * description : 页面数据返回体
 *
 * @author : zhencai.cheng
 */
public class ResponseJson {

    private int code = ResponseCode.REQUEST_SUCCESS; //响应状态
    private String msg = "正常调用"; //响应状态说明
    private Object data = new Object(); //响应数据
    private String traceID;

    public ResponseJson() {
        traceID = RequestThreadContext.getTraceID();
    }


    public ResponseJson(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
    }

    public ResponseJson(Object data) {
        this();
        if (data != null) {
            this.data = data;
        }
    }


    public ResponseJson(int code, String msg, Object data) {
        this(code, msg);
        if (data != null) {
            this.data = data;
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }
}
