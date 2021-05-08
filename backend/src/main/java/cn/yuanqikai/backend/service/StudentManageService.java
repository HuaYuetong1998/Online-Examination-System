package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.StudentInfoIdsDTO;
import cn.yuanqikai.backend.dto.StudentManageSearchDTO;
import cn.yuanqikai.backend.entity.StudentClass;
import cn.yuanqikai.backend.entity.StudentInfo;
import cn.yuanqikai.backend.entity.User;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-25 09:48
 */
public interface StudentManageService {

    Page<StudentInfo> selectAllStudent(Integer currentPage, Integer pageSize);

    Page<StudentInfo> selectStudentByMulti(StudentManageSearchDTO studentManageSearchDTO);

    int addStudentToClass(StudentClass studentClass);

    int selectClassRepeat(StudentClass studentClass);

    int deleteByClassId(Integer classId);

    StudentInfo selectByStudentId(Integer studentId);

    List<Integer> selectByClassId(Integer classId);

    Page<StudentInfo> selectStudentInfoByIds(StudentInfoIdsDTO studentInfoIdsDTO);

    int deleteByStudentId(StudentClass studentClass);
}
