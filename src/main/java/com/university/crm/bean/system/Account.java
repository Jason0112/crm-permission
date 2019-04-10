package com.university.crm.bean.system;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class Account implements Serializable {
    /**
     * 账号ID
     */
    private Integer accountID;

    /**
     * 姓名
     */
    @FieldNote(name = "姓名", type = FieldEnum.STRING, isNullAble = false)
    private String realName;
    /**
     * 账号
     */
    @FieldNote(name = "登录账号", type = FieldEnum.STRING, isNullAble = false)
    private String account;
    /**
     * 密码
     */
    @FieldNote(name = "密码", type = FieldEnum.STRING, isNullAble = false)
    private String password;
    /**
     * 密码盐
     */
    private String passwordSalt;
    /**
     * 状态 状态
     */
    private Integer state;
    /**
     * 备注 备注
     */
    private String remark;
    /**
     * 最后登录ip
     */
    private String lastLoginIP;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 部门标识
     */
    private String departmentSign;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 创建时间
     */
    private Date updatedTime;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDepartmentSign() {
        return departmentSign;
    }

    public void setDepartmentSign(String departmentSign) {
        this.departmentSign = departmentSign;
    }
}
