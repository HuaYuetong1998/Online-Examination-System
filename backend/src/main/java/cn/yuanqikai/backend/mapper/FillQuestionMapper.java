package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.FillQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FillQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(FillQuestion record);

    int insertSelective(FillQuestion record);

    FillQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(FillQuestion record);

    int updateByPrimaryKey(FillQuestion record);

    int countBySubjects(List<Integer> subjectIds);

    List<FillQuestion> randomSelectFill(@Param("list") List<Integer> subjectIds, @Param("num") Integer Num);
}