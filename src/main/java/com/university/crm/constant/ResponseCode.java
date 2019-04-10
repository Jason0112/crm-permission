package com.university.crm.constant;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
public interface ResponseCode {

    /**
     * 调用成功响应码
     */
    int REQUEST_SUCCESS = 200;

    /**
     * 程序异常
     */
    int REQUEST_ERROR_PROGRAM_EXCEPTION = -100;

    /**
     * ip限制
     */
    int REQUEST_ERROR_REFUSE_IP = -60;
}
