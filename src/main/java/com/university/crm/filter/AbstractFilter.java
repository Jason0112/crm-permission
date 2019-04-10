package com.university.crm.filter;


import com.university.crm.constant.Constant;
import com.university.crm.util.HashKit;

/**
 * date: 2018/11/15
 * description :
 *
 * @author : zhencai.cheng
 */
class AbstractFilter {



    String getAccount(String authCookieValue) {
        if (authCookieValue != null && !authCookieValue.trim().isEmpty() && !authCookieValue.trim().equals("null")) {
            String[] authCookieValues = authCookieValue.split("\\|");
            if (authCookieValues.length == 2) { //cookie值数组长度为2
                String account = authCookieValues[0];
                String sign = authCookieValues[1];
                if (sign.equals(HashKit.md5HashAddSalt(account, Constant.authWebKey))) {
                    return account;
                }
            }
        }
        return null;
    }
}
