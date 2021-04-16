package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.AutoCreatePaperDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.dto.SearchQuestionDTO;
import cn.yuanqikai.backend.dto.SubjectIdsDTO;
import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.FillQuestion;
import cn.yuanqikai.backend.entity.JudgeQuestion;
import cn.yuanqikai.backend.entity.SubjectiveQuestion;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-07 15:12
 */
public interface QuestionsPoolService {

    int addChoiceQuestion(ChoiceQuestion choiceQuestion);

    int addFillQuestion(FillQuestion fillQuestion);

    int addJudgeQuestion(JudgeQuestion judgeQuestion);

    int addSubjectiveQuestion(SubjectiveQuestion subjectiveQuestion);

    List<ChoiceQuestion> findQuestionMultiCondition(SearchQuestionDTO searchQuestionDTO);

    Page<ChoiceQuestion> findByPage(SearchQuestionDTO searchQuestionDTO);

    ChoiceQuestion findChoiceQuestionById(Integer questionId);

    QuestionDTO findOtherQuestionById(Integer questionId);

    int saveChoiceQuestion(ChoiceQuestion choiceQuestion);

    int saveFillQuestion(FillQuestion fillQuestion);

    int saveJudgeQuestion(JudgeQuestion judgeQuestion);

    int saveSubjectiveQuestion(SubjectiveQuestion subjectiveQuestion);

    int deleteChoiceQuestion(Integer questionId);

    int deleteFillQuestion(Integer questionId);

    int deleteJudgeQuestion(Integer questionId);

    int deleteSubjectiveQuestion(Integer questionId);

    Page<ChoiceQuestion> selectBySubjectIds(SubjectIdsDTO subjectIdsDTO);

    int countChoiceBySubject(AutoCreatePaperDTO autoCreatePaperDTO);

    int countFillBySubject(AutoCreatePaperDTO autoCreatePaperDTO);

    int countJudgeBySubject(AutoCreatePaperDTO autoCreatePaperDTO);

    int countSubjectiveBySubject(AutoCreatePaperDTO autoCreatePaperDTO);

    List<ChoiceQuestion> randomSelectChoice(AutoCreatePaperDTO autoCreatePaperDTO);

    List<FillQuestion> randomSelectFill(AutoCreatePaperDTO autoCreatePaperDTO);

    List<JudgeQuestion> randomSelectJudge(AutoCreatePaperDTO autoCreatePaperDTO);

    List<SubjectiveQuestion> randomSelectSubjective(AutoCreatePaperDTO autoCreatePaperDTO);
}
