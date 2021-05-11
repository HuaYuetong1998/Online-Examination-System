package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.dto.ExamStatusDTO;
import cn.yuanqikai.backend.dto.SituationDTO;
import cn.yuanqikai.backend.entity.Exam;
import cn.yuanqikai.backend.entity.ExamStudent;
import cn.yuanqikai.backend.entity.StudentSituation;
import cn.yuanqikai.backend.mapper.ExamMapper;
import cn.yuanqikai.backend.mapper.ExamStudentMapper;
import cn.yuanqikai.backend.service.ExamManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanqikai
 * @date 2021-04-26 09:40
 */
@Service
public class ExamManageServiceImpl implements ExamManageService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamStudentMapper examStudentMapper;

    @Override
    public int addExam(ExamDTO examDTO) throws ParseException {
        Exam exam = new Exam();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = simpleDateFormat.parse(examDTO.getExamStartTime());
        Date endTime = simpleDateFormat.parse(examDTO.getExamEndTime());
        exam.setExamTitle(examDTO.getExamTitle());
        exam.setPaperId(examDTO.getPaperId());
        exam.setTimeLimit(examDTO.getExamTimeDuration());
        exam.setStartTime(startTime);
        exam.setOverTime(endTime);
        exam.setStatus(0);
        exam.setIsRandom(examDTO.getIsRandom());
        exam.setIsSnap(examDTO.getIsSnap());
        examMapper.insertSelective(exam);
        return exam.getExamId();
    }

    @Override
    public int addExamStudent(ExamStudent examStudent) {
        return examStudentMapper.insertSelective(examStudent);
    }

    @Override
    public Page<Exam> selectByMulti(ExamStatusDTO examStatusDTO) {
        PageHelper.startPage(examStatusDTO.getCurrentPage(),examStatusDTO.getPageSize());
        return examMapper.selectExamByMulti(examStatusDTO);
    }

    @Override
    public Page<StudentSituation> selectByStudentId(SituationDTO situationDTO) {
        PageHelper.startPage(situationDTO.getCurrentPage(),situationDTO.getPageSize());
        return examStudentMapper.selectByStudentId(situationDTO);
    }
}
