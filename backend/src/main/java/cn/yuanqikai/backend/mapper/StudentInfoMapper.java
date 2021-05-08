package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.StudentManageSearchDTO;
import cn.yuanqikai.backend.entity.StudentInfo;
import com.github.pagehelper.Page;

import java.util.List;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer studentId);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);

    Page<StudentInfo> selectAllStudent();

    Page<StudentInfo> selectByStudentIds(List<Integer> list);

    Page<StudentInfo> selectByMulti(StudentManageSearchDTO studentManageSearchDTO);

    StudentInfo selectByTel(String tel);
}