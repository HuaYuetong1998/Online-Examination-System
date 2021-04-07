package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.FillQuestion;
import cn.yuanqikai.backend.entity.JudgeQuestion;
import cn.yuanqikai.backend.entity.SubjectiveQuestion;
import cn.yuanqikai.backend.mapper.ChoiceQuestionMapper;
import cn.yuanqikai.backend.mapper.FillQuestionMapper;
import cn.yuanqikai.backend.mapper.JudgeQuestionMapper;
import cn.yuanqikai.backend.mapper.SubjectiveQuestionMapper;
import cn.yuanqikai.backend.service.QuestionsPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
