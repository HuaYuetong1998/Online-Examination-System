package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.JudgeQuestion;

public interface JudgeQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(JudgeQuestion record);

    int insertSelective(JudgeQuestion record);

    JudgeQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(JudgeQuestion record);

    int updateByPrimaryKey(JudgeQuestion record);
}