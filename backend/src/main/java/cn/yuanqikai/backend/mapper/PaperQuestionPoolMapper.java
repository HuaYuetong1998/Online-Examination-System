package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.PaperQuestionPool;

import java.util.List;

public interface PaperQuestionPoolMapper {
    int insert(PaperQuestionPool record);

    int insertSelective(PaperQuestionPool record);

    List<Integer> selectPaperQuestionIds(Integer paperId);

    int deleteByPaperId(Integer paperId);
}