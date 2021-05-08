package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.dto.ExamDisplayDTO;
import cn.yuanqikai.backend.dto.ExamStatusDTO;
import cn.yuanqikai.backend.entity.Exam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamMapper {
    int deleteByPrimaryKey(Integer examId);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);

    List<Exam> selectOnGoingExam(List<Integer> list);

    List<Exam> selectOveredExam(List<Integer> list);

    int updateStatusById(Integer examId);

    Page<Exam> selectExamByMulti(ExamStatusDTO examStatusDTO);
}