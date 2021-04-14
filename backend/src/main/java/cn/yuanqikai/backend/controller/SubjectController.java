package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.DeleteSubjectDTO;
import cn.yuanqikai.backend.dto.SubjectDTO;
import cn.yuanqikai.backend.entity.Subject;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.SubjectService;
import cn.yuanqikai.backend.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-04-08 15:53
 */
@RestController
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/api/subject")
    public DataResponse findAllSubject() {
        List<Subject> subjects = subjectService.findAll();
        return DataResponse.success().data(subjects);
    }

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @PostMapping("/api/subject/add")
    public DataResponse addSubject(@RequestBody Subject subject) {
        // 检查数据库里是否已经存在重名的subject
        Subject selectSubject = subjectService.findByName(subject.getSubjectName());
        if(selectSubject != null) {
            return DataResponse.fail().msg("学科已经存在");
        }
        int res = subjectService.addSubject(subject);
        if(res != 0) {
            // 获取数据库里添加进去的subject
            Subject resultSubject = subjectService.findByName(subject.getSubjectName());
            return DataResponse.success().data(resultSubject);
        }
        return  DataResponse.fail().msg("添加失败");
    }

    @PostMapping("/api/subject/delete")
    public DataResponse deleteSubject(@RequestBody DeleteSubjectDTO deleteList) {
        List<String> list = deleteList.getDeleteList();
        if (list.size() > 0) {
            subjectService.deleteByName(list);
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/subject/getId")
    public DataResponse getSubjectId(@RequestParam String subject) {
        if(StringUtils.isNotEmpty(subject)) {
            Subject subjectByName = subjectService.findByName(subject);
            Integer subjectId = subjectByName.getSubjectId();
            List<Subject> subjects = subjectService.findAll();
            Map<String,Object> subjectMap = new HashMap<>();
            subjectMap.put("subjectName",subject);
            subjectMap.put("currentSubjectId",subjectId);
            subjectMap.put("subjects",subjects);
            return DataResponse.success().data(subjectMap);
        }
        logger.error(subject);
       return DataResponse.fail();
    }
}
