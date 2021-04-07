package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.JudgeQuestion;

public interface JudgeQuestionMapper {
    int insert(JudgeQuestion record);

    int insertSelective(JudgeQuestion record);
}