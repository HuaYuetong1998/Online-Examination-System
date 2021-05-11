package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.SearchStudentInfoDTO;
import cn.yuanqikai.backend.dto.StudentClassDTO;
import cn.yuanqikai.backend.dto.StudentInfoIdsDTO;
import cn.yuanqikai.backend.dto.StudentManageSearchDTO;
import cn.yuanqikai.backend.entity.StudentClass;
import cn.yuanqikai.backend.entity.StudentInfo;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.StudentManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-25 09:58
 */
@RestController
public class StudentManageController {
    @Autowired
    private StudentManageService studentManageService;

    @GetMapping("/api/addToClass/student")
    public DataResponse addToClass(@RequestParam Integer currentPage,@RequestParam Integer pageSize) {
        Page<StudentInfo> users = studentManageService.selectAllStudent(currentPage, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageData", users);
        map.put("pageTotal", users.getTotal());
        return DataResponse.success().data(map);
    }

    @GetMapping("/api/addToClass/init")
    public DataResponse initAllStudent(@RequestParam Integer classId) {
        List<Integer> studentIdList = studentManageService.selectByClassId(classId);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for(Integer studentId : studentIdList) {
            StudentInfo studentInfo = studentManageService.selectByStudentId(studentId);
            studentInfos.add(studentInfo);
        }
        return DataResponse.success().data(studentInfos);
    }

    @PostMapping("/api/class/addStudent")
    public DataResponse addClassStudent(@RequestBody StudentClassDTO studentClassDTO) {
        Integer classId = studentClassDTO.getClassId();
        List<StudentInfo> studentInfos = studentClassDTO.getStudentInfos();
        if (classId != null) {
            studentManageService.deleteByClassId(classId);
        }
        for(StudentInfo studentInfo: studentInfos) {
            StudentClass studentClass = new StudentClass();
            studentClass.setStudentId(studentInfo.getStudentId());
            studentClass.setClassId(classId);
            int count = studentManageService.selectClassRepeat(studentClass);
            if(count != 0) {
                continue;
            }
            studentManageService.addStudentToClass(studentClass);
        }
        return DataResponse.success();
    }

    @PostMapping("/api/class/searchStudent")
    public DataResponse searchStudent(@RequestBody SearchStudentInfoDTO searchStudentInfoDTO) {
        Integer classId = searchStudentInfoDTO.getClassId();
        Integer currentPage = searchStudentInfoDTO.getCurrentPage();
        Integer pageSize = searchStudentInfoDTO.getPageSize();
        List<Integer> studentIdList = studentManageService.selectByClassId(classId);
        StudentInfoIdsDTO studentInfoIdsDTO = new StudentInfoIdsDTO();
        studentInfoIdsDTO.setCurrentPage(currentPage);
        studentInfoIdsDTO.setPageSize(pageSize);
        studentInfoIdsDTO.setList(studentIdList);
        Page<StudentInfo> studentInfos = studentManageService.selectStudentInfoByIds(studentInfoIdsDTO);

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageData",studentInfos);
        map.put("pageTotal",studentInfos.getTotal());
        return DataResponse.success().data(map);
    }

    @PostMapping("/api/addToClass/delete")
    public DataResponse deleteClassStudent(@RequestBody StudentClass studentClass) {
        int res = studentManageService.deleteByStudentId(studentClass);
        if(res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/studentManage/search")
    public DataResponse searchStudentInfo(@RequestBody StudentManageSearchDTO studentManageSearchDTO){
        Page<StudentInfo> studentInfos = studentManageService.selectStudentByMulti(studentManageSearchDTO);

        HashMap<String, Object> map = new HashMap<>();
        map.put("studentInfos",studentInfos);
        map.put("pageTotal",studentInfos.getTotal());
        return  DataResponse.success().data(map);
    }

    @GetMapping("/api/student/situation")
    public DataResponse getStudentInfo(@RequestParam Integer studentId) {
        StudentInfo studentInfo = studentManageService.selectByStudentId(studentId);
        return DataResponse.success().data(studentInfo);
    }

}
