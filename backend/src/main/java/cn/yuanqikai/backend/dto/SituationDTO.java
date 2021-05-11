package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-05-08 15:57
 */
public class SituationDTO {
    private Integer studentId;

    private Integer currentPage;

    private Integer pageSize;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
