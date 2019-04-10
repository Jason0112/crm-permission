package com.university.crm.bean;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/18
 * description : 分数
 *
 * @author : zhencai.cheng
 */
public class Score {

    private Integer scoreID;

    private Integer userID;

    private Integer subjectID;

    @FieldNote(name = "得分", type = FieldEnum.NUMBER, isNullAble = false)
    private Double score;

    private Date createTime;

    public Integer getScoreID() {
        return scoreID;
    }

    public void setScoreID(Integer scoreID) {
        this.scoreID = scoreID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
