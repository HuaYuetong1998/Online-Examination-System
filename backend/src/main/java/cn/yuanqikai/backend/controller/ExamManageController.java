package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.dto.ExamStatusDTO;
import cn.yuanqikai.backend.dto.SituationDTO;
import cn.yuanqikai.backend.entity.*;
import cn.yuanqikai.backend.mapper.*;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.ExamManageService;
import cn.yuanqikai.backend.utils.StringUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-26 09:40
 */
@RestController
public class ExamManageController {

    @Autowired
    private StudentClassMapper studentClassMapper;

    @Autowired
    private ExamManageService examManageService;

    @Autowired
    private AnswerSheetMapper answerSheetMapper;

    @Autowired
    private ChoiceQuestionMapper choiceQuestionMapper;

    @Autowired
    private FillQuestionMapper fillQuestionMapper;

    @Autowired
    private JudgeQuestionMapper judgeQuestionMapper;

    @Autowired
    private SubjectiveQuestionMapper subjectiveQuestionMapper;

    @Autowired
    private ExamStudentMapper examStudentMapper;

    @PostMapping("/api/exam/add")
    public DataResponse addExam(@RequestBody ExamDTO examDTO) throws ParseException {
        List<Integer> studentIds = new ArrayList<>();
        if(examDTO.getClassId() != null){
            Integer classId = examDTO.getClassId();
            studentIds = studentClassMapper.selectByClassId(classId);
        }
        if(examDTO.getExamStudentIds().size() > 0) {
            studentIds = examDTO.getExamStudentIds();
        }

        int examId = examManageService.addExam(examDTO);
        if(examId > 0) {
            for(Integer studentId : studentIds) {
                ExamStudent examStudent = new ExamStudent();
                examStudent.setExamId(examId);
                examStudent.setStudentId(studentId);
                examManageService.addExamStudent(examStudent);
            }
            return DataResponse.success();
        }
        return DataResponse.fail();

    }

    @PostMapping("/api/exam/search")
    public DataResponse searchExam(@RequestBody ExamStatusDTO examStatusDTO) {

        Page<Exam> exams = examManageService.selectByMulti(examStatusDTO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("exams",exams);
        map.put("pageTotal",exams.getTotal());
        return DataResponse.success().data(map);
    }

    @PostMapping("/api/exam/situation")
    public DataResponse searchSituation(@RequestBody SituationDTO situationDTO) {
        Page<StudentSituation> examStudents = examManageService.selectByStudentId(situationDTO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("examStudents",examStudents);
        map.put("pageTotal",examStudents.getTotal());
        return DataResponse.success().data(map);
    }

    @GetMapping("/api/exam/getAnswerSheet")
    public DataResponse getAnswerSheet(@RequestParam Integer studentId, @RequestParam Integer examId) {
        String answerSheet = answerSheetMapper.selectAnswerSheet(studentId, examId);
        if(StringUtils.isNotEmpty(answerSheet)) {
            return DataResponse.success().data(answerSheet);
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/exam/getChoiceRightAnswer")
    public DataResponse getChoiceRightAnswer(@RequestParam Integer questionId) {
        ChoiceQuestion choiceQuestion = choiceQuestionMapper.selectQuestionDetail(questionId);
        return DataResponse.success().data(choiceQuestion);
    }

    @GetMapping("/api/exam/getFillRightAnswer")
    public DataResponse getFillRightAnswer(@RequestParam Integer questionId) {
        FillQuestion fillQuestion = fillQuestionMapper.selectByPrimaryKey(questionId);
        return DataResponse.success().data(fillQuestion);
    }

    @GetMapping("/api/exam/getJudgeRightAnswer")
    public DataResponse getJudgeRightAnswer(@RequestParam Integer questionId) {
        JudgeQuestion judgeQuestion = judgeQuestionMapper.selectByPrimaryKey(questionId);
        return DataResponse.success().data(judgeQuestion);
    }

    @GetMapping("/api/exam/getSubjectiveRightAnswer")
    public DataResponse getSubjectiveRightAnswer(@RequestParam Integer questionId) {
        SubjectiveQuestion subjectiveQuestion = subjectiveQuestionMapper.selectByPrimaryKey(questionId);
        return DataResponse.success().data(subjectiveQuestion);
    }


    @PostMapping("/api/exam/updateCorrect")
    public DataResponse updateCorrect(@RequestBody ExamStudent examStudent) {
        int res = examStudentMapper.updateCorrect(examStudent);
        if(res > 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }
}
