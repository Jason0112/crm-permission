package com.university.crm.bean;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/18
 * description : 科目
 *
 * @author : zhencai.cheng
 */
public class Course {

    private Integer subjectID;

    @FieldNote(name = "科目", type = FieldEnum.STRING, isNullAble = false)
    private String courseName;

    private Date createTime;

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
