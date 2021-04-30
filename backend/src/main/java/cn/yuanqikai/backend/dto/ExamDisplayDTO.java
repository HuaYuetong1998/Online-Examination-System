package cn.yuanqikai.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author yuanqikai
 * @date 2021-04-27 10:52
 */
public class ExamDisplayDTO {
    private Integer examId;

    private Integer paperId;

    private String examTitle;

    private Integer timeLimit;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date overTime;

    private Integer status;

    private Integer isRandom;

    private Integer isSnap;

    private Integer totalScore;

    private Integer questionsNum;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle == null ? null : examTitle.trim();
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(Integer isRandom) {
        this.isRandom = isRandom;
    }

    public Integer getIsSnap() {
        return isSnap;
    }

    public void setIsSnap(Integer isSnap) {
        this.isSnap = isSnap;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(Integer questionsNum) {
        this.questionsNum = questionsNum;
    }

}
