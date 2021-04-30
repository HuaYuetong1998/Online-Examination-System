package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.PaperDTO;
import cn.yuanqikai.backend.dto.PaperListDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.Paper;
import cn.yuanqikai.backend.entity.Subject;
import cn.yuanqikai.backend.mapper.PaperMapper;
import cn.yuanqikai.backend.mapper.SubjectMapper;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.PaperManageService;
import cn.yuanqikai.backend.service.SubjectService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-16 14:01
 */

@RestController
public class PaperManageController {

    @Autowired
    private PaperManageService paperManageService;

    @Autowired
    private SubjectMapper subjectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping("/api/paper/isExist")
    public DataResponse isPaperExist(@RequestParam String paperTitle) {
        int res = paperManageService.countByPaperTitle(paperTitle);
        if (res != 0) {
            return DataResponse.fail().msg("试卷已存在");
        }
        return DataResponse.success();
    }

    @PostMapping("/api/paper/add")
    public DataResponse addPaper(@RequestBody PaperDTO paperDTO) {

        int res = paperManageService.addPaper(paperDTO);
        if (res != 0) {
            paperManageService.addPaperQuestionPool(paperDTO);
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/paper/list")
    public DataResponse findPaper(@RequestBody PaperDTO paperDTO) {
        logger.warn("================== " + paperDTO.getType() +" =====================");
        Page<PaperDTO> paperDTOS = paperManageService.selectMultiCondition(paperDTO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageData", paperDTOS);
        map.put("pageTotal", paperDTOS.getTotal());

        return DataResponse.success().data(map);
    }

    @GetMapping("/api/paper/detail")
    public DataResponse paperDetail(@RequestParam Integer paperId ) {
        Paper paper = paperManageService.selectById(paperId);
        List<Integer> questionIds = paperManageService.selectPaperQuestionIds(paperId);
        List<QuestionDTO> questionDetail = new ArrayList<>();
        for (Integer questionId : questionIds) {
            ChoiceQuestion question = paperManageService.selectQuestionDetail(questionId);
            Subject subject = subjectMapper.selectByPrimaryKey(question.getSubjectId());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(question.getQuestionId());
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setSubject(subject.getSubjectName());
            questionDTO.setAnswerA(question.getAnswerA());
            questionDTO.setAnswerB(question.getAnswerB());
            questionDTO.setAnswerC(question.getAnswerC());
            questionDTO.setAnswerD(question.getAnswerD());
            questionDTO.setRightAnswer(question.getRightAnswer());
            questionDTO.setAnalysis(question.getAnalysis());
            questionDTO.setScore(question.getScore());
            questionDTO.setLevel(question.getLevel());
            questionDetail.add(questionDTO);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("paper",paper);
        map.put("questionDetail",questionDetail);
        return DataResponse.success().data(map);
    }

    @PostMapping("/api/paper/modify")
    public DataResponse paperModify(@RequestBody PaperDTO paperDTO) {
        int res = paperManageService.updatePaper(paperDTO);
        if(res != 0) {
            paperManageService.updatePaperQuestionPool(paperDTO);
            return DataResponse.success();
        }
        return  DataResponse.fail();
    }

    @GetMapping("/api/paper/delete")
    public DataResponse paperDelete(@RequestParam Integer paperId) {
        int res = paperManageService.deletePaper(paperId);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/paper/getAll")
    public DataResponse getAllPaper() {
        List<Paper> papers = paperManageService.selectAllPaper();
        ArrayList<PaperListDTO> paperList = new ArrayList<>();
        for(Paper paper : papers) {
            PaperListDTO paperListDTO = new PaperListDTO();
            paperListDTO.setPaperTitle(paper.getPaperTitle());
            paperListDTO.setPaperId(paper.getPaperId());
            paperList.add(paperListDTO);
        }
        return DataResponse.success().data(paperList);
    }
}
