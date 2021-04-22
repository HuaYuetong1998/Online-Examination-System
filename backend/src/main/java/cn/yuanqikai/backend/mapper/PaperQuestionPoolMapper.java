package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.PaperQuestionPool;

public interface PaperQuestionPoolMapper {
    int insert(PaperQuestionPool record);

    int insertSelective(PaperQuestionPool record);
}