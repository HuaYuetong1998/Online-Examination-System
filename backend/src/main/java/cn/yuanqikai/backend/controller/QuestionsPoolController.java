package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.FillQuestion;
import cn.yuanqikai.backend.entity.JudgeQuestion;
import cn.yuanqikai.backend.entity.SubjectiveQuestion;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.QuestionsPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanqikai
 * @date 2021-04-07 15:50
 */
@RestController
public class QuestionsPoolController {

    @Autowired
    private QuestionsPoolService questionsPoolService;

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
}
