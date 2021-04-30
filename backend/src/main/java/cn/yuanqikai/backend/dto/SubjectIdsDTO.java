package cn.yuanqikai.backend.dto;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-15 10:28
 */
public class SubjectIdsDTO {
    private List<Integer> subjectIds;

    private Integer PageSize;

    private Integer CurrentPage;

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        CurrentPage = currentPage;
    }
}
