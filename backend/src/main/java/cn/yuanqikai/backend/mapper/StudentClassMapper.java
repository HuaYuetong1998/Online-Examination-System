package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.StudentClass;

import java.util.List;

public interface StudentClassMapper {
    int insert(StudentClass record);

    int insertSelective(StudentClass record);

    int deleteByClassId(Integer classId);

    int selectRepeat(StudentClass studentClass);

    List<Integer> selectByClassId(Integer classId);

    int deleteByStudentId(StudentClass studentClass);
}