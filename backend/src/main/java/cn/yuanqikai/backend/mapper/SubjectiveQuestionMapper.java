package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.SubjectiveQuestion;

public interface SubjectiveQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(SubjectiveQuestion record);

    int insertSelective(SubjectiveQuestion record);

    SubjectiveQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(SubjectiveQuestion record);

    int updateByPrimaryKey(SubjectiveQuestion record);
}