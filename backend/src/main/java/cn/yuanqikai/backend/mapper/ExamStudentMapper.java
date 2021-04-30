package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.ExamStudent;

import java.util.List;

public interface ExamStudentMapper {
    int insert(ExamStudent record);

    int insertSelective(ExamStudent record);

    List<Integer> selectExamByStudentId(Integer studentId);
}