package com.university.crm.bean;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/18
 * description :科目考试
 *
 * @author : zhencai.cheng
 */
public class CourseExam {

    @FieldNote(name = "用户ID", type = FieldEnum.NUMBER, isNullAble = false)
    private Integer userID;

    @FieldNote(name = "考生编号", type = FieldEnum.STRING, isNullAble = false)
    private String examNO;

    @FieldNote(name = "考试开始时间", type = FieldEnum.DATE)
    private Date examBeginTime;

    @FieldNote(name = "考试结束时间", type = FieldEnum.DATE)
    private Date examEndTime;

    private Date createTime;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getExamNO() {
        return examNO;
    }

    public void setExamNO(String examNO) {
        this.examNO = examNO;
    }

    public Date getExamBeginTime() {
        return examBeginTime;
    }

    public void setExamBeginTime(Date examBeginTime) {
        this.examBeginTime = examBeginTime;
    }

    public Date getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
