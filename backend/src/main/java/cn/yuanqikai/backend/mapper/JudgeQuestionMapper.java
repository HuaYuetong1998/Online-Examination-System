package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.JudgeQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JudgeQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(JudgeQuestion record);

    int insertSelective(JudgeQuestion record);

    JudgeQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(JudgeQuestion record);

    int updateByPrimaryKey(JudgeQuestion record);

    int countBySubjects(List<Integer> subjectIds);

    List<JudgeQuestion> randomSelectJudge(@Param("list") List<Integer> subjectIds, @Param("num") Integer Num);
}