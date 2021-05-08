package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.dto.ExamStatusDTO;
import cn.yuanqikai.backend.entity.Exam;
import cn.yuanqikai.backend.entity.ExamStudent;
import com.github.pagehelper.Page;

import java.text.ParseException;

/**
 * @author yuanqikai
 * @date 2021-04-26 09:39
 */
public interface ExamManageService {
    int addExam(ExamDTO examDTO) throws ParseException;

    int addExamStudent(ExamStudent examStudent);

    Page<Exam> selectByMulti(ExamStatusDTO examStatusDTO);
}
