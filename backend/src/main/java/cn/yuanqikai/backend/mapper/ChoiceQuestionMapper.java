package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.SearchQuestionDTO;
import cn.yuanqikai.backend.entity.ChoiceQuestion;
import com.github.pagehelper.Page;

import java.util.List;

public interface ChoiceQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(ChoiceQuestion record);

    int insertSelective(ChoiceQuestion record);

    ChoiceQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(ChoiceQuestion record);

    int updateByPrimaryKey(ChoiceQuestion record);

    Page<ChoiceQuestion> selectMultiCondition(SearchQuestionDTO searchQuestionDTO);
}