package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.entity.Subject;
import cn.yuanqikai.backend.mapper.SubjectMapper;
import cn.yuanqikai.backend.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-08 15:50
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectMapper subjectMapper;

    @Override
    public List<Subject> findAll() {
        return subjectMapper.selectAll();
    }

    @Override
    public int addSubject(Subject subject) {
        return subjectMapper.insert(subject);
    }

    @Override
    public Subject findByName(String subjectName) {
        return subjectMapper.selectByName(subjectName);
    }

    @Override
    public void deleteByName(List<String> deleteList) {
        for (String subjectName : deleteList) {
            subjectMapper.deleteBySubjectName(subjectName);
        }
    }
}
