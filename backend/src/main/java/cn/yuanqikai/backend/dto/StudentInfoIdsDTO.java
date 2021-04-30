package cn.yuanqikai.backend.dto;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-25 17:34
 */
public class StudentInfoIdsDTO {

    private List<Integer> list;

    private Integer currentPage;

    private Integer pageSize;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
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
