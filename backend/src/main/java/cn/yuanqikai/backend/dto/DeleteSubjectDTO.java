package cn.yuanqikai.backend.dto;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-09 14:40
 */
public class DeleteSubjectDTO {

    private List<String> deleteList;

    public List<String> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<String> deleteList) {
        this.deleteList = deleteList;
    }
}
