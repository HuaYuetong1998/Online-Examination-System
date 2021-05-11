package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.SituationDTO;
import cn.yuanqikai.backend.entity.ExamStudent;
import cn.yuanqikai.backend.entity.StudentSituation;
import com.github.pagehelper.Page;

import java.util.List;

public interface ExamStudentMapper {
    int insert(ExamStudent record);

    int insertSelective(ExamStudent record);

    List<Integer> selectExamByStudentId(Integer studentId);

    int updateStatus(ExamStudent examStudent);

    int updateCorrect(ExamStudent examStudent);

    Page<StudentSituation> selectByStudentId(SituationDTO situationDTO);
}