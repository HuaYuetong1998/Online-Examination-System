package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.ExamDTO;
import cn.yuanqikai.backend.entity.ExamStudent;
import cn.yuanqikai.backend.mapper.ExamMapper;
import cn.yuanqikai.backend.mapper.StudentClassMapper;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.ExamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
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
}
