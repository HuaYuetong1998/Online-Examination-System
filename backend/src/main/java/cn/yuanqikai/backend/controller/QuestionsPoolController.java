package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.*;
import cn.yuanqikai.backend.entity.*;
import cn.yuanqikai.backend.mapper.SubjectMapper;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.QuestionsPoolService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-04-07 15:50
 */
@RestController
public class QuestionsPoolController {

    @Autowired
    private QuestionsPoolService questionsPoolService;

    @Autowired
    private SubjectMapper subjectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping("/api/choiceQuestion/add")
    public DataResponse addChoiceQuestion(@RequestBody ChoiceQuestion choiceQuestion) {
        int res = questionsPoolService.addChoiceQuestion(choiceQuestion);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/fillQuestion/add")
    public DataResponse addFillQuestion(@RequestBody FillQuestion fillQuestion) {
        int res = questionsPoolService.addFillQuestion(fillQuestion);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/judgeQuestion/add")
    public DataResponse addJudgeQuestion(@RequestBody JudgeQuestion judgeQuestion) {
        int res = questionsPoolService.addJudgeQuestion(judgeQuestion);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/subjectiveQuestion/add")
    public DataResponse addSubjectiveQuestion(@RequestBody SubjectiveQuestion subjectiveQuestion) {
        int res = questionsPoolService.addSubjectiveQuestion(subjectiveQuestion);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/question/list")
    public DataResponse findQuestionMultiCondition(@RequestBody SearchQuestionDTO searchQuestionDTO) {
        Page<ChoiceQuestion> page = questionsPoolService.findByPage(searchQuestionDTO);
        //List<ChoiceQuestion> choiceQuestions = questionsPoolService.findQuestionMultiCondition(searchQuestionDTO);
        if (page != null) {
            Page<QuestionDTO> questionDTOS = new Page<>();
            for (ChoiceQuestion choiceQuestion : page.getResult()) {
                Integer subjectId = choiceQuestion.getSubjectId();
                Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(choiceQuestion.getQuestionId());
                questionDTO.setSubject(subject.getSubjectName());
                questionDTO.setAnswerA(choiceQuestion.getAnswerA());
                questionDTO.setAnswerB(choiceQuestion.getAnswerB());
                questionDTO.setAnswerC(choiceQuestion.getAnswerC());
                questionDTO.setAnswerD(choiceQuestion.getAnswerD());
                questionDTO.setRightAnswer(choiceQuestion.getRightAnswer());
                questionDTO.setAnalysis(choiceQuestion.getAnalysis());
                questionDTO.setQuestion(choiceQuestion.getQuestion());
                questionDTO.setScore(choiceQuestion.getScore());
                questionDTO.setLevel(choiceQuestion.getLevel());
                questionDTOS.add(questionDTO);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageData", questionDTOS);
            map.put("pageTotal", page.getTotal());
            return DataResponse.success().data(map);
        }
        return DataResponse.fail().msg("无查询记录");
    }

    @GetMapping("/api/question/getChoiceQuestionDetail")
    public DataResponse findChoiceQuestionById(@RequestParam Integer questionId) {
        ChoiceQuestion choiceQuestion = questionsPoolService.findChoiceQuestionById(questionId);
        Subject subject = subjectMapper.selectByPrimaryKey(choiceQuestion.getSubjectId());
        ChoiceQuestionDTO choiceQuestionDTO = new ChoiceQuestionDTO();
        choiceQuestionDTO.setQuestionId(choiceQuestion.getQuestionId());
        choiceQuestionDTO.setQuestion(choiceQuestion.getQuestion());
        choiceQuestionDTO.setSubject(subject.getSubjectName());
        choiceQuestionDTO.setAnswerA(choiceQuestion.getAnswerA());
        choiceQuestionDTO.setAnswerB(choiceQuestion.getAnswerB());
        choiceQuestionDTO.setAnswerC(choiceQuestion.getAnswerC());
        choiceQuestionDTO.setAnswerD(choiceQuestion.getAnswerD());
        choiceQuestionDTO.setRightAnswer(choiceQuestion.getRightAnswer());
        choiceQuestionDTO.setAnalysis(choiceQuestion.getAnalysis());
        choiceQuestionDTO.setScore(choiceQuestion.getScore());
        choiceQuestionDTO.setLevel(choiceQuestion.getLevel());
        return DataResponse.success().data(choiceQuestionDTO);
    }

    @GetMapping("/api/question/getOtherQuestionDetail")
    public DataResponse findOtherQuestionById(@RequestParam Integer questionId) {
        QuestionDTO otherQuestion = questionsPoolService.findOtherQuestionById(questionId);
        logger.warn(otherQuestion.toString());
        return DataResponse.success().data(otherQuestion);
    }

    @PostMapping("/api/choiceQuestion/save")
    public DataResponse saveChoiceQuestion(@RequestBody ChoiceQuestion choiceQuestion) {
        int res = questionsPoolService.saveChoiceQuestion(choiceQuestion);
        if (res != 0) {
            return DataResponse.success().data(choiceQuestion);
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/fillQuestion/save")
    public DataResponse saveFillQuestion(@RequestBody FillQuestion fillQuestion) {
        int res = questionsPoolService.saveFillQuestion(fillQuestion);
        if (res != 0) {
            return DataResponse.success().data(fillQuestion);
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/judgeQuestion/save")
    public DataResponse saveJudgeQuestion(@RequestBody JudgeQuestion judgeQuestion) {
        int res = questionsPoolService.saveJudgeQuestion(judgeQuestion);
        if (res != 0) {
            return DataResponse.success().data(judgeQuestion);
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/subjectiveQuestion/save")
    public DataResponse saveSubjectiveQuestion(@RequestBody SubjectiveQuestion subjectiveQuestion) {
        int res = questionsPoolService.saveSubjectiveQuestion(subjectiveQuestion);
        if (res != 0) {
            return DataResponse.success().data(subjectiveQuestion);
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/choiceQuestion/delete")
    public DataResponse deleteChoiceQuestion(@RequestParam Integer questionId) {
        int res = questionsPoolService.deleteChoiceQuestion(questionId);
        if (res != 0) {
            return DataResponse.success().msg("删除成功");
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/fillQuestion/delete")
    public DataResponse deleteFillQuestion(@RequestParam Integer questionId) {
        int res = questionsPoolService.deleteFillQuestion(questionId);
        if (res != 0) {
            return DataResponse.success().msg("删除成功");
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/judgeQuestion/delete")
    public DataResponse deleteJudgeQuestion(@RequestParam Integer questionId) {
        int res = questionsPoolService.deleteJudgeQuestion(questionId);
        if (res != 0) {
            return DataResponse.success().msg("删除成功");
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/subjectiveQuestion/delete")
    public DataResponse deleteSubjectiveQuestion(@RequestParam Integer questionId) {
        int res = questionsPoolService.deleteSubjectiveQuestion(questionId);
        if (res != 0) {
            return DataResponse.success().msg("删除成功");
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/question/paperQuestion")
    public DataResponse selectBySubjectIds(@RequestBody SubjectIdsDTO subjectIdsDTO) {
        if(subjectIdsDTO.getSubjectIds().isEmpty()){
            return DataResponse.fail().msg("学科不能为空");
        }
        Page<ChoiceQuestion> page = questionsPoolService.selectBySubjectIds(subjectIdsDTO);
        if (page != null) {
            Page<QuestionDTO> questionDTOS = new Page<>();
            for (ChoiceQuestion choiceQuestion : page.getResult()) {
                Integer subjectId = choiceQuestion.getSubjectId();
                Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(choiceQuestion.getQuestionId());
                questionDTO.setSubject(subject.getSubjectName());
                questionDTO.setAnswerA(choiceQuestion.getAnswerA());
                questionDTO.setAnswerB(choiceQuestion.getAnswerB());
                questionDTO.setAnswerC(choiceQuestion.getAnswerC());
                questionDTO.setAnswerD(choiceQuestion.getAnswerD());
                questionDTO.setRightAnswer(choiceQuestion.getRightAnswer());
                questionDTO.setAnalysis(choiceQuestion.getAnalysis());
                questionDTO.setQuestion(choiceQuestion.getQuestion());
                questionDTO.setScore(choiceQuestion.getScore());
                questionDTO.setLevel(choiceQuestion.getLevel());
                questionDTOS.add(questionDTO);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageData", questionDTOS);
            map.put("pageTotal", page.getTotal());
            return DataResponse.success().data(map);
        }

        return DataResponse.fail();
    }

    @PostMapping("/api/question/getMax")
    public DataResponse getMaxQuestionNum(@RequestBody AutoCreatePaperDTO autoCreatePaperDTO) {
        Integer choiceNum = questionsPoolService.countChoiceBySubject(autoCreatePaperDTO);
        Integer fillNum = questionsPoolService.countFillBySubject(autoCreatePaperDTO);
        Integer judgeNum = questionsPoolService.countJudgeBySubject(autoCreatePaperDTO);
        Integer subjectiveNum = questionsPoolService.countSubjectiveBySubject(autoCreatePaperDTO);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("choiceNum",choiceNum);
        map.put("fillNum",fillNum);
        map.put("judgeNum",judgeNum);
        map.put("subjectiveNum",subjectiveNum);
        return DataResponse.success().data(map);
    }

    @PostMapping("/api/question/autoCreatePaper")
    public DataResponse autoCreatePaper(@RequestBody AutoCreatePaperDTO autoCreatePaperDTO) {
        if(autoCreatePaperDTO.getSubjectIds().isEmpty()){
            return DataResponse.fail().msg("学科为空");
        }
        List<ChoiceQuestion> choiceQuestions = questionsPoolService.randomSelectChoice(autoCreatePaperDTO);
        List<FillQuestion> fillQuestions = questionsPoolService.randomSelectFill(autoCreatePaperDTO);
        List<JudgeQuestion> judgeQuestions = questionsPoolService.randomSelectJudge(autoCreatePaperDTO);
        List<SubjectiveQuestion> subjectiveQuestions = questionsPoolService.randomSelectSubjective(autoCreatePaperDTO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("choiceQuestion",choiceQuestions);
        map.put("fillQuestion",fillQuestions);
        map.put("judgeQuestion",judgeQuestions);
        map.put("subjectiveQuestion",subjectiveQuestions);
        return DataResponse.success().data(map);
    }
}
