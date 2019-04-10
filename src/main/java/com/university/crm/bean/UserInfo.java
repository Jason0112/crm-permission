package com.university.crm.bean;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/18
 * description : 用户信息
 *
 * @author : zhencai.cheng
 */
public class UserInfo {

    private Integer userID;

    @FieldNote(name = "登录账号", type = FieldEnum.STRING, isNullAble = false)
    private String account;

    @FieldNote(name = "登录账号", type = FieldEnum.STRING, isNullAble = false)
    private String mobile;

    @FieldNote(name = "登录账号", type = FieldEnum.STRING, isNullAble = false)
    private String password;

    private String passwordSalt;

    private Integer state;

    @FieldNote(name = "身份证", type = FieldEnum.STRING, isNullAble = false)
    private String idCard;

    private Date birthday;

    private String email;

    private String realName;

    private String nickName;

    private Integer gender;

    private String province;

    private String city;

    private String district;

    private Date registerTime;

    private Date createTime;

    private Date updateTime;


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
