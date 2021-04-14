package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.ChoiceQuestionDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.dto.SearchQuestionDTO;
import cn.yuanqikai.backend.dto.SubjectDTO;
import cn.yuanqikai.backend.entity.*;
import cn.yuanqikai.backend.mapper.SubjectMapper;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.QuestionsPoolService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
}
