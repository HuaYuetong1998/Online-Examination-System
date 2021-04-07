package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.SubjectiveQuestion;

public interface SubjectiveQuestionMapper {
    int insert(SubjectiveQuestion record);

    int insertSelective(SubjectiveQuestion record);
}