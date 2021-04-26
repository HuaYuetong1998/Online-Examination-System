package cn.yuanqikai.backend.dto;

import cn.yuanqikai.backend.entity.StudentInfo;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-25 15:42
 */
public class StudentClassDTO {
    private Integer classId;

    private List<StudentInfo> studentInfos;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public List<StudentInfo> getStudentInfos() {
        return studentInfos;
    }

    public void setStudentInfos(List<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
    }
}
