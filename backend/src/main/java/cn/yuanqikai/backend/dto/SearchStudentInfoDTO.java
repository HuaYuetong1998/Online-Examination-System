package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-04-25 16:19
 */
public class SearchStudentInfoDTO {
    private Integer classId;

    private Integer pageSize;

    private Integer currentPage;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
