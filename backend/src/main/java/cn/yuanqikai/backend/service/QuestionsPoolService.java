package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.FillQuestion;
import cn.yuanqikai.backend.entity.JudgeQuestion;
import cn.yuanqikai.backend.entity.SubjectiveQuestion;
import cn.yuanqikai.backend.response.BaseResponse;

/**
 * @author yuanqikai
 * @date 2021-04-07 15:12
 */
public interface QuestionsPoolService {

    int addChoiceQuestion(ChoiceQuestion choiceQuestion);

    int addFillQuestion(FillQuestion fillQuestion);

    int addJudgeQuestion(JudgeQuestion judgeQuestion);

    int addSubjectiveQuestion(SubjectiveQuestion subjectiveQuestion);

}
