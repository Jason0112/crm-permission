package com.university.crm.exception;



import com.university.crm.bean.base.ResponseJson;
import com.university.crm.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * date: 2018/11/29
 * description :
 *
 * @author : zhencai.cheng
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BusinessException.class)
    public ResponseJson handleBusinessException(BusinessException e) {
        LOGGER.error("业务异常:{}", e.getMessage());
        return new ResponseJson(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseJson handleException(Exception e) {
        LOGGER.error("程序异常:", e);
        return new ResponseJson(ResponseCode.REQUEST_ERROR_PROGRAM_EXCEPTION, e.getMessage());
    }
}