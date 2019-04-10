package com.university.crm.bean.system;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class Department {

    private Integer departmentID;

    @FieldNote(name = "部门名称", type = FieldEnum.STRING, isNullAble = false)
    private String departmentName;


    @FieldNote(name = "部门标识", type = FieldEnum.STRING, isNullAble = false)
    private String departmentSign;


    private Integer departmentState;


    private String remark;


    private String parentSign;


    private Date createdTime;

    private Date updatedTime;

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentSign() {
        return departmentSign;
    }

    public void setDepartmentSign(String departmentSign) {
        this.departmentSign = departmentSign;
    }

    public Integer getDepartmentState() {
        return departmentState;
    }

    public void setDepartmentState(Integer departmentState) {
        this.departmentState = departmentState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentSign() {
        return parentSign;
    }

    public void setParentSign(String parentSign) {
        this.parentSign = parentSign;
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
}