package com.university.crm.bean;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/27
 * description : 专业
 *
 * @author : zhencai.cheng
 */
public class Major {

    private Integer majorID;

    @FieldNote(name = "专业名称", type = FieldEnum.STRING, isNullAble = false)
    private String majorName;

    private Date createTime;

    private Date updateTime;


    public Integer getMajorID() {
        return majorID;
    }

    public void setMajorID(Integer majorID) {
        this.majorID = majorID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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
}
