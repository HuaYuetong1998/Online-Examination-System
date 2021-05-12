package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.ExamDisplayDTO;
import cn.yuanqikai.backend.entity.AnswerSheet;
import cn.yuanqikai.backend.entity.Exam;
import cn.yuanqikai.backend.entity.ExamStudent;
import cn.yuanqikai.backend.entity.Paper;
import cn.yuanqikai.backend.mapper.ExamMapper;
import cn.yuanqikai.backend.mapper.ExamStudentMapper;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.PaperManageService;
import cn.yuanqikai.backend.service.StudentExamDisplayService;
import cn.yuanqikai.backend.vo.ExamDisplayVO;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-04-27 10:29
 */
@RestController
public class StudentExamDisplayController {

    @Autowired
    private StudentExamDisplayService studentExamDisplayService;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamStudentMapper examStudentMapper;

    @Autowired
    private PaperManageService paperManageService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping("/api/front/exam/getOnGoing")
    public DataResponse getExamOnGoing(@RequestBody ExamDisplayVO examDisplayVO) {
        List<Integer> examIds = studentExamDisplayService.selectExamByStudentId(examDisplayVO.getStudentId());
        examDisplayVO.setExamIds(examIds);

        PageInfo<ExamDisplayDTO> examOnGoingPageInfo = studentExamDisplayService.selectExamOnGoing(examDisplayVO);

        HashMap<String, Object> map = new HashMap<>();

        map.put("examOnGoingData",examOnGoingPageInfo.getList());
        map.put("examOnGoingTotal",examOnGoingPageInfo.getTotal());
        return DataResponse.success().data(map);
    }


    @PostMapping("/api/front/exam/getOvered")
    public DataResponse getExamOvered(@RequestBody ExamDisplayVO examDisplayVO) {
        List<Integer> examIds = studentExamDisplayService.selectExamByStudentId(examDisplayVO.getStudentId());
        examDisplayVO.setExamIds(examIds);


        PageInfo<ExamDisplayDTO> examOveredPageInfo = studentExamDisplayService.selectExamOvered(examDisplayVO);

        HashMap<String, Object> map = new HashMap<>();

        map.put("examOveredData",examOveredPageInfo.getList());
        map.put("examOveredTotal",examOveredPageInfo.getTotal());
        return DataResponse.success().data(map);
    }

    @GetMapping("/api/examPage/getExamInfo")
    public DataResponse getExamInfo(@RequestParam Integer examId) {
        Exam exam = examMapper.selectByPrimaryKey(examId);
        HashMap<String, Object> map = new HashMap<>();
        if (exam != null) {
            map.put("examData",exam);
            Integer paperId = exam.getPaperId();
            Paper paper = paperManageService.selectById(paperId);
            if (paper != null) {
                map.put("paperData",paper);
                return DataResponse.success().data(map);
            }
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/examPage/getQuestions")
    public DataResponse getQuestions(@RequestParam Integer paperId) {
        Map<String, Object> questionList = studentExamDisplayService.getQuestionList(paperId);
        return DataResponse.success().data(questionList);
    }

    @PostMapping("/api/examPage/answerSheet")
    public DataResponse saveAnswerSheet(@RequestBody AnswerSheet answerSheet) {
        logger.error(answerSheet.getSubmitTime().toString());
        int res = studentExamDisplayService.saveAnswerSheet(answerSheet);
        if (res > 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/examPage/examStatus")
    public DataResponse switchExamStatus(@RequestParam Integer examId) {
        int res = examMapper.updateStatusById(examId);
        if(res > 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/examPage/submitStatus")
    public DataResponse switchSubmitStatus(@RequestBody ExamStudent examStudent) {
        int res = examStudentMapper.updateStatus(examStudent);
        if(res > 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }
}
