package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.dto.StudentInfoIdsDTO;
import cn.yuanqikai.backend.entity.StudentClass;
import cn.yuanqikai.backend.entity.StudentInfo;
import cn.yuanqikai.backend.mapper.StudentClassMapper;
import cn.yuanqikai.backend.mapper.StudentInfoMapper;
import cn.yuanqikai.backend.service.StudentManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-25 09:48
 */
@Service
public class StudentManageServiceImpl implements StudentManageService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private StudentClassMapper studentClassMapper;

    @Override
    public Page<StudentInfo> selectAllStudent(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        return studentInfoMapper.selectAllStudent();
    }

    @Override
    public int addStudentToClass(StudentClass studentClass) {
        return studentClassMapper.insertSelective(studentClass);
    }

    @Override
    public int selectClassRepeat(StudentClass studentClass) {
        return studentClassMapper.selectRepeat(studentClass);
    }

    @Override
    public StudentInfo selectByStudentId(Integer studentId) {
        return studentInfoMapper.selectByPrimaryKey(studentId);
    }

    @Override
    public int deleteByClassId(Integer classId) {
        return studentClassMapper.deleteByClassId(classId);
    }

    @Override
    public List<Integer> selectByClassId(Integer classId) {
        return studentClassMapper.selectByClassId(classId);
    }

    @Override
    public Page<StudentInfo> selectStudentInfoByIds(StudentInfoIdsDTO studentInfoIdsDTO) {
        Integer currentPage = studentInfoIdsDTO.getCurrentPage();
        Integer pageSize = studentInfoIdsDTO.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        return studentInfoMapper.selectByStudentIds(studentInfoIdsDTO.getList());
    }

    @Override
    public int deleteByStudentId(StudentClass studentClass) {
        return studentClassMapper.deleteByStudentId(studentClass);
    }
}
