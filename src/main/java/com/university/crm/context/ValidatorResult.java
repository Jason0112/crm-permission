package com.university.crm.context;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * date: 2018/8/18
 * description : 属性验证返回结果
 *
 * @author : zhencai.cheng
 */
public class ValidatorResult {
    /**
     * 返回标识
     */
    private boolean success = true;
    /**
     * 错误消息代码
     */
    private Set<String> errorCodes;

    public ValidatorResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        if (null == errorCodes) {
            errorCodes = new HashSet<>();
        }
        return errorCodes;
    }

    public void addError(String errorCode) {
        if (errorCode == null || "".equals(errorCode.trim())) {
            return;
        }
        if (!getErrorCodes().contains(errorCode)) {
            getErrorCodes().add(errorCode);
        }
        success = false;
    }

    public void addErrors(Collection<String> errors) {
        getErrorCodes().addAll(errors);
        success = false;
    }

    public String getErrorStr() {
        String str = errorCodes.toString();
        return str.substring(1, str.length() - 1);
    }

}
