package cn.yuanqikai.backend.dto;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-15 16:44
 */
public class AutoCreatePaperDTO {
    private List<Integer> subjectIds;

    private Integer choiceNum;

    private Integer fillNum;

    private Integer judgeNum;

    private Integer subjectiveNum;

    private Integer choiceScore;

    private Integer fillScore;

    private Integer judgeScore;

    private Integer subjectiveScore;

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public Integer getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(Integer choiceNum) {
        this.choiceNum = choiceNum;
    }

    public Integer getFillNum() {
        return fillNum;
    }

    public void setFillNum(Integer fillNum) {
        this.fillNum = fillNum;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public Integer getSubjectiveNum() {
        return subjectiveNum;
    }

    public void setSubjectiveNum(Integer subjectiveNum) {
        this.subjectiveNum = subjectiveNum;
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
}
