package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.entity.FillQuestion;

public interface FillQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(FillQuestion record);

    int insertSelective(FillQuestion record);

    FillQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(FillQuestion record);

    int updateByPrimaryKey(FillQuestion record);
}