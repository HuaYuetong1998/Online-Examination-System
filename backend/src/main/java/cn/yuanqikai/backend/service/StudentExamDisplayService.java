package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.ExamDisplayDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.entity.AnswerSheet;
import cn.yuanqikai.backend.entity.Exam;
import cn.yuanqikai.backend.vo.ExamDisplayVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-04-27 10:20
 */
public interface StudentExamDisplayService {

    List<Integer> selectExamByStudentId(Integer studentId);

    PageInfo<ExamDisplayDTO> selectExamOnGoing(ExamDisplayVO examDisplayVO);

    PageInfo<ExamDisplayDTO> selectExamOvered(ExamDisplayVO examDisplayVO);

    Exam selectExamById(Integer examId);

    Map<String,Object> getQuestionList(Integer paperId);

    int saveAnswerSheet(AnswerSheet answerSheet);
}
