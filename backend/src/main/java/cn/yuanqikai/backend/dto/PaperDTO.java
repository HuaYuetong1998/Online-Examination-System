package cn.yuanqikai.backend.dto;

import java.util.Date;

/**
 * @author yuanqikai
 * @date 2021-04-16 13:51
 */
public class PaperDTO {
    private Integer paperId;

    private String paperTitle;

    private Integer choiceNum;

    private Integer normalChoiceNum;

    private Integer fillNum;

    private Integer normalFillNum;

    private Integer judgeNum;

    private Integer normalJudgeNum;

    private Integer subjectiveNum;

    private Integer normalSubjectiveNum;

    private Integer choiceScore;

    private Integer fillScore;

    private Integer judgeScore;

    private Integer subjectiveScore;

    private Integer status;

    private Integer totalScore;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Integer getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(Integer choiceNum) {
        this.choiceNum = choiceNum;
    }

    public Integer getNormalChoiceNum() {
        return normalChoiceNum;
    }

    public void setNormalChoiceNum(Integer normalChoiceNum) {
        this.normalChoiceNum = normalChoiceNum;
    }

    public Integer getFillNum() {
        return fillNum;
    }

    public void setFillNum(Integer fillNum) {
        this.fillNum = fillNum;
    }

    public Integer getNormalFillNum() {
        return normalFillNum;
    }

    public void setNormalFillNum(Integer normalFillNum) {
        this.normalFillNum = normalFillNum;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public Integer getNormalJudgeNum() {
        return normalJudgeNum;
    }

    public void setNormalJudgeNum(Integer normalJudgeNum) {
        this.normalJudgeNum = normalJudgeNum;
    }

    public Integer getSubjectiveNum() {
        return subjectiveNum;
    }

    public void setSubjectiveNum(Integer subjectiveNum) {
        this.subjectiveNum = subjectiveNum;
    }

    public Integer getNormalSubjectiveNum() {
        return normalSubjectiveNum;
    }

    public void setNormalSubjectiveNum(Integer normalSubjectiveNum) {
        this.normalSubjectiveNum = normalSubjectiveNum;
    }

    public Integer getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(Integer choiceScore) {
        this.choiceScore = choiceScore;
    }

    public Integer getFillScore() {
        return fillScore;
    }

    public void setFillScore(Integer fillScore) {
        this.fillScore = fillScore;
    }

    public Integer getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Integer judgeScore) {
        this.judgeScore = judgeScore;
    }

    public Integer getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(Integer subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
