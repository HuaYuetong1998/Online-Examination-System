package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-05-08 10:30
 */
public class StudentManageSearchDTO {

    private Integer studentId;

    private String realName;

    private Integer pageSize;

    private Integer currentPage;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
