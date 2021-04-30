package cn.yuanqikai.backend.dto;

import java.util.Date;
import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-26 16:38
 */
public class ExamDTO {

    private String examTitle;

    private Integer paperId;

    private Integer classId;

    private List<Integer> examStudentIds;

    private String examStartTime;

    private String examEndTime;

    private Integer examTimeDuration;

    private Integer isRandom;

    private Integer isSnap;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public List<Integer> getExamStudentIds() {
        return examStudentIds;
    }

    public void setExamStudentIds(List<Integer> examStudentIds) {
        this.examStudentIds = examStudentIds;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Integer getExamTimeDuration() {
        return examTimeDuration;
    }

    public void setExamTimeDuration(Integer examTimeDuration) {
        this.examTimeDuration = examTimeDuration;
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
}
