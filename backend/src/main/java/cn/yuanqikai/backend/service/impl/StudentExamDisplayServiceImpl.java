package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.controller.RegisterController;
import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.dto.ExamDisplayDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.entity.*;
import cn.yuanqikai.backend.mapper.*;
import cn.yuanqikai.backend.service.StudentExamDisplayService;
import cn.yuanqikai.backend.vo.ExamDisplayVO;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuanqikai
 * @date 2021-04-27 10:21
 */
@Service
public class StudentExamDisplayServiceImpl implements StudentExamDisplayService {

    @Autowired
    private ExamStudentMapper examStudentMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperQuestionPoolMapper paperQuestionPoolMapper;

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

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Override
    public List<Integer> selectExamByStudentId(Integer studentId) {
        return examStudentMapper.selectExamByStudentId(studentId);
    }


    @Override
    public PageInfo<ExamDisplayDTO> selectExamOnGoing(ExamDisplayVO examDisplayVO) {
        List<Integer> examIds = examDisplayVO.getExamIds();
        PageInfo<Exam> source = PageHelper.startPage(examDisplayVO.getOnGoingCurrentPage(), examDisplayVO.getOnGoingPageSize()).doSelectPageInfo(() -> {
            examMapper.selectOnGoingExam(examIds);
        });
        // 需要转化的对象
        PageInfo<ExamDisplayDTO> target = new PageInfo<>();

        //复制分页属性
        BeanUtils.copyProperties(source, target);
        List<Exam> exams = source.getList().stream().collect(Collectors.toList());
        List<ExamDisplayDTO> examOnGoingDisplay = new ArrayList<>();

        for (Exam exam : exams) {
            ExamDisplayDTO examDisplayDTO = new ExamDisplayDTO();
            Integer paperId = exam.getPaperId();
            Paper paper = paperMapper.selectByPrimaryKey(paperId);
            Integer questionsNum = paper.getChoiceNum() + paper.getFillNum() + paper.getJudgeNum() + paper.getSubjectiveNum();
            examDisplayDTO.setExamId(exam.getExamId());
            examDisplayDTO.setExamTitle(exam.getExamTitle());
            examDisplayDTO.setTimeLimit(exam.getTimeLimit());
            examDisplayDTO.setPaperId(paperId);
            examDisplayDTO.setStartTime(exam.getStartTime());
            examDisplayDTO.setOverTime(exam.getOverTime());
            examDisplayDTO.setStatus(exam.getStatus());
            examDisplayDTO.setIsRandom(exam.getIsRandom());
            examDisplayDTO.setIsSnap(exam.getIsSnap());
            examDisplayDTO.setTotalScore(paper.getTotalScore());
            examDisplayDTO.setQuestionsNum(questionsNum);

            examOnGoingDisplay.add(examDisplayDTO);

        }

        target.setList(examOnGoingDisplay);
        return target;
    }


    @Override
    public PageInfo<ExamDisplayDTO> selectExamOvered(ExamDisplayVO examDisplayVO) {
        List<Integer> examIds = examDisplayVO.getExamIds();
        PageInfo<Exam> source = PageHelper.startPage(examDisplayVO.getOveredCurrentPage(), examDisplayVO.getOveredPageSize()).doSelectPageInfo(() -> {
            examMapper.selectOveredExam(examIds);
        });

        PageInfo<ExamDisplayDTO> target = new PageInfo<>();
        BeanUtils.copyProperties(source, target);
        List<Exam> exams = source.getList().stream().collect(Collectors.toList());
        List<ExamDisplayDTO> examOveredDisplay = new ArrayList<>();

        for (Exam exam : exams) {
            ExamDisplayDTO examDisplayDTO = new ExamDisplayDTO();
            Integer paperId = exam.getPaperId();
            Paper paper = paperMapper.selectByPrimaryKey(paperId);
            Integer questionsNum = paper.getChoiceNum() + paper.getFillNum() + paper.getJudgeNum() + paper.getSubjectiveNum();
            examDisplayDTO.setExamId(exam.getExamId());
            examDisplayDTO.setExamTitle(exam.getExamTitle());
            examDisplayDTO.setTimeLimit(exam.getTimeLimit());
            examDisplayDTO.setPaperId(paperId);
            examDisplayDTO.setStartTime(exam.getStartTime());
            examDisplayDTO.setOverTime(exam.getOverTime());
            examDisplayDTO.setStatus(exam.getStatus());
            examDisplayDTO.setIsRandom(exam.getIsRandom());
            examDisplayDTO.setIsSnap(exam.getIsSnap());
            examDisplayDTO.setTotalScore(paper.getTotalScore());
            examDisplayDTO.setQuestionsNum(questionsNum);

            examOveredDisplay.add(examDisplayDTO);

        }
        target.setList(examOveredDisplay);
        return target;
    }

    @Override
    public Exam selectExamById(Integer examId) {
        return examMapper.selectByPrimaryKey(examId);
    }

    @Override
    public Map<String,Object> getQuestionList(Integer paperId) {
        List<Integer> questionIds = paperQuestionPoolMapper.selectPaperQuestionIds(paperId);

        List<ChoiceQuestion> choiceQuestions = new ArrayList<>();
        List<FillQuestion> fillQuestions = new ArrayList<>();
        List<JudgeQuestion> judgeQuestions = new ArrayList<>();
        List<SubjectiveQuestion> subjectiveQuestions = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();

        for(Integer questionId : questionIds) {
            if (questionId >= 100000 && questionId < 200000) {
                ChoiceQuestion choiceQuestion = choiceQuestionMapper.selectByPrimaryKey(questionId);
                choiceQuestions.add(choiceQuestion);
            } else if (questionId >= 200000 && questionId < 300000) {
                FillQuestion fillQuestion = fillQuestionMapper.selectByPrimaryKey(questionId);
                fillQuestions.add(fillQuestion);
            } else if (questionId >= 300000 && questionId < 400000) {
                JudgeQuestion judgeQuestion = judgeQuestionMapper.selectByPrimaryKey(questionId);
                judgeQuestions.add(judgeQuestion);
            } else if (questionId >= 400000) {
                SubjectiveQuestion subjectiveQuestion = subjectiveQuestionMapper.selectByPrimaryKey(questionId);
                subjectiveQuestions.add(subjectiveQuestion);
            }

        }

        if (choiceQuestions.size() > 0) {
            map.put("choiceQuestions",choiceQuestions);
        }

        if (fillQuestions.size() > 0) {
            map.put("fillQuestions",fillQuestions);
        }

        if (judgeQuestions.size() > 0) {
            map.put("judgeQuestions",judgeQuestions);
        }

        if (subjectiveQuestions.size() > 0) {
            map.put("subjectiveQuestions",subjectiveQuestions);
        }
        return map;
    }

    @Override
    public int saveAnswerSheet(AnswerSheet answerSheet) {
        int count = answerSheetMapper.selectRepeat(answerSheet.getExamId(), answerSheet.getStudentId());
        if (count > 0) {
            return answerSheetMapper.updateAnswerSheet(answerSheet);
        }else {
            return answerSheetMapper.insert(answerSheet);
        }

    }
}
