package com.university.crm.data.dto.search;

/**
 * date: 2019/3/18
 * description : 账号搜索条件
 *
 * @author : zhencai.cheng
 */
public class AccountSearch {

    private String realName;

    private String account;

    private Integer state;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
