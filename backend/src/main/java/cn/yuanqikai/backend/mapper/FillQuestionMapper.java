package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.FillQuestion;

public interface FillQuestionMapper {
    int insert(FillQuestion record);

    int insertSelective(FillQuestion record);
}