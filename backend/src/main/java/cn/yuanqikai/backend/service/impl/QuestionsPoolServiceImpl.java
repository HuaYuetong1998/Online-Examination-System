package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.dto.SearchQuestionDTO;
import cn.yuanqikai.backend.entity.*;
import cn.yuanqikai.backend.mapper.*;
import cn.yuanqikai.backend.service.QuestionsPoolService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-07 15:13
 */
@Service
public class QuestionsPoolServiceImpl implements QuestionsPoolService {

    @Autowired
    private ChoiceQuestionMapper choiceQuestionMapper;

    @Autowired
    private FillQuestionMapper fillQuestionMapper;

    @Autowired
    private JudgeQuestionMapper judgeQuestionMapper;

    @Autowired
    private SubjectiveQuestionMapper subjectiveQuestionMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public int addChoiceQuestion(ChoiceQuestion choiceQuestion) {
        return choiceQuestionMapper.insert(choiceQuestion);
    }

    @Override
    public int addFillQuestion(FillQuestion fillQuestion) {
        return fillQuestionMapper.insert(fillQuestion);
    }

    @Override
    public int addJudgeQuestion(JudgeQuestion judgeQuestion) {
        return judgeQuestionMapper.insert(judgeQuestion);
    }

    @Override
    public int addSubjectiveQuestion(SubjectiveQuestion subjectiveQuestion) {
        return subjectiveQuestionMapper.insert(subjectiveQuestion);
    }

    @Override
    public List<ChoiceQuestion> findQuestionMultiCondition(SearchQuestionDTO searchQuestionDTO) {
       return choiceQuestionMapper.selectMultiCondition(searchQuestionDTO);
    }

    @Override
    public Page<ChoiceQuestion> findByPage(SearchQuestionDTO searchQuestionDTO) {
        PageHelper.startPage(searchQuestionDTO.getCurrentPage(), searchQuestionDTO.getPageSize());
        return choiceQuestionMapper.selectMultiCondition(searchQuestionDTO);
    }

    @Override
    public ChoiceQuestion findChoiceQuestionById(Integer questionId) {
        return choiceQuestionMapper.selectByPrimaryKey(questionId);
    }

    @Override
    public QuestionDTO findOtherQuestionById(Integer questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        if (questionId >= 200000 && questionId < 300000) {
            FillQuestion fillQuestion = fillQuestionMapper.selectByPrimaryKey(questionId);
            Subject subject = subjectMapper.selectByPrimaryKey(fillQuestion.getSubjectId());
            questionDTO.setQuestionId(fillQuestion.getQuestionId());
            questionDTO.setSubject(subject.getSubjectName());
            questionDTO.setQuestion(fillQuestion.getQuestion());
            questionDTO.setRightAnswer(fillQuestion.getAnswer());
            questionDTO.setAnalysis(fillQuestion.getAnalysis());
            questionDTO.setScore(fillQuestion.getScore());
            questionDTO.setLevel(fillQuestion.getLevel());
        } else if (questionId >= 300000 && questionId < 400000) {
            JudgeQuestion judgeQuestion = judgeQuestionMapper.selectByPrimaryKey(questionId);
            Subject subject = subjectMapper.selectByPrimaryKey(judgeQuestion.getSubjectId());
            questionDTO.setQuestionId(judgeQuestion.getQuestionId());
            questionDTO.setSubject(subject.getSubjectName());
            questionDTO.setQuestion(judgeQuestion.getQuestion());
            questionDTO.setRightAnswer(judgeQuestion.getAnswer());
            questionDTO.setAnalysis(judgeQuestion.getAnalysis());
            questionDTO.setScore(judgeQuestion.getScore());
            questionDTO.setLevel(judgeQuestion.getLevel());
        }else {
            SubjectiveQuestion subjectiveQuestion = subjectiveQuestionMapper.selectByPrimaryKey(questionId);
            Subject subject = subjectMapper.selectByPrimaryKey(subjectiveQuestion.getSubjectId());
            questionDTO.setQuestionId(subjectiveQuestion.getQuestionId());
            questionDTO.setSubject(subject.getSubjectName());
            questionDTO.setQuestion(subjectiveQuestion.getQuestion());
            questionDTO.setRightAnswer(subjectiveQuestion.getAnswer());
            questionDTO.setAnalysis(subjectiveQuestion.getAnalysis());
            questionDTO.setScore(subjectiveQuestion.getScore());
            questionDTO.setLevel(subjectiveQuestion.getLevel());
        }
        return questionDTO;
    }

    @Override
    public int saveChoiceQuestion(ChoiceQuestion choiceQuestion) {
        return choiceQuestionMapper.updateByPrimaryKeySelective(choiceQuestion);
    }

    @Override
    public int saveFillQuestion(FillQuestion fillQuestion) {
        return fillQuestionMapper.updateByPrimaryKeySelective(fillQuestion);
    }

    @Override
    public int saveJudgeQuestion(JudgeQuestion judgeQuestion) {
        return judgeQuestionMapper.updateByPrimaryKeySelective(judgeQuestion);
    }

    @Override
    public int saveSubjectiveQuestion(SubjectiveQuestion subjectiveQuestion) {
        return subjectiveQuestionMapper.updateByPrimaryKeySelective(subjectiveQuestion);
    }

    @Override
    public int deleteChoiceQuestion(Integer questionId) {
        return choiceQuestionMapper.deleteByPrimaryKey(questionId);
    }

    @Override
    public int deleteFillQuestion(Integer questionId) {
        return fillQuestionMapper.deleteByPrimaryKey(questionId);
    }

    @Override
    public int deleteJudgeQuestion(Integer questionId) {
        return judgeQuestionMapper.deleteByPrimaryKey(questionId);
    }

    @Override
    public int deleteSubjectiveQuestion(Integer questionId) {
        return subjectiveQuestionMapper.deleteByPrimaryKey(questionId);
    }
}
