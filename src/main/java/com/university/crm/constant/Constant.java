package com.university.crm.constant;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
public interface Constant {

    String PAGE_SIZE = "pageSize";

    String START_ROW = "startRow";

    String TOTAL = "total";

    String ROWS = "rows";

    String STATUS = "status";


    String loginURL = "/login.action";

    //cookie web认证密钥
    String authWebKey = "8W73JNFKO66TR7V8I99N80234R2SUKOF";

    //cookie 统一key
    String authCookieName = "accountToken";

    //cookie 路径
    String authCookiePath = "/";

    String authCookieDomain = "university.com";

    //cookie过期时间
    int cookieMaxAge = -1;
}
