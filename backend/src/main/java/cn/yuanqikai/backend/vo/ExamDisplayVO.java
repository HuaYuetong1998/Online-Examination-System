package cn.yuanqikai.backend.vo;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-27 11:01
 */
public class ExamDisplayVO {
    private Integer studentId;

    private Integer onGoingCurrentPage;

    private Integer onGoingPageSize;

    private Integer overedCurrentPage;

    private Integer overedPageSize;

    private List<Integer> examIds;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getOnGoingCurrentPage() {
        return onGoingCurrentPage;
    }

    public void setOnGoingCurrentPage(Integer onGoingCurrentPage) {
        this.onGoingCurrentPage = onGoingCurrentPage;
    }

    public Integer getOnGoingPageSize() {
        return onGoingPageSize;
    }

    public void setOnGoingPageSize(Integer onGoingPageSize) {
        this.onGoingPageSize = onGoingPageSize;
    }

    public Integer getOveredCurrentPage() {
        return overedCurrentPage;
    }

    public void setOveredCurrentPage(Integer overedCurrentPage) {
        this.overedCurrentPage = overedCurrentPage;
    }

    public Integer getOveredPageSize() {
        return overedPageSize;
    }

    public void setOveredPageSize(Integer overedPageSize) {
        this.overedPageSize = overedPageSize;
    }

    public List<Integer> getExamIds() {
        return examIds;
    }

    public void setExamIds(List<Integer> examIds) {
        this.examIds = examIds;
    }
}
