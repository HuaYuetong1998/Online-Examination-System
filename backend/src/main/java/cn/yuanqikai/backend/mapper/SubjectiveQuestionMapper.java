package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.SubjectiveQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectiveQuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(SubjectiveQuestion record);

    int insertSelective(SubjectiveQuestion record);

    SubjectiveQuestion selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(SubjectiveQuestion record);

    int updateByPrimaryKey(SubjectiveQuestion record);

    int countBySubjects(List<Integer> subjectIds);

    List<SubjectiveQuestion> randomSelectSubjective(@Param("list") List<Integer> subjectIds, @Param("num") Integer Num);
}