package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.SubjectDTO;
import cn.yuanqikai.backend.entity.Subject;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-08 15:50
 */
public interface SubjectService {

    List<Subject> findAll();

    int addSubject(Subject subject);

    Subject findByName(String subjectName);

    void deleteByName(List<String> deleteList);
}
