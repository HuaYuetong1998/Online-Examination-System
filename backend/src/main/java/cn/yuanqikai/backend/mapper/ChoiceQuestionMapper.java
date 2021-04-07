package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.ChoiceQuestion;

public interface ChoiceQuestionMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(ChoiceQuestion record);

    int insertSelective(ChoiceQuestion record);

    ChoiceQuestion selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(ChoiceQuestion record);

    int updateByPrimaryKey(ChoiceQuestion record);
}