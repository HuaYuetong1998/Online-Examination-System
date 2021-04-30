package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.Subject;

import java.util.List;

public interface SubjectMapper {
    int deleteByPrimaryKey(Integer subjectId);

    int deleteBySubjectName(String subjectName);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Integer subjectId);

    List<Subject> selectAll();

    Subject selectByName(String subjectName);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);
}