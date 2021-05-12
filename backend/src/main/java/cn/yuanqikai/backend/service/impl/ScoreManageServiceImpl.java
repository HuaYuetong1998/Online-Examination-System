package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.entity.Exam;
import cn.yuanqikai.backend.entity.Score;
import cn.yuanqikai.backend.entity.StudentInfo;
import cn.yuanqikai.backend.mapper.ExamMapper;
import cn.yuanqikai.backend.mapper.ScoreMapper;
import cn.yuanqikai.backend.mapper.StudentInfoMapper;
import cn.yuanqikai.backend.service.ScoreManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yuanqikai
 * @date 2021-05-11 15:24
 */
@Service
public class ScoreManageServiceImpl implements ScoreManageService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public int saveScore(Score score) {
        return scoreMapper.insert(score);
    }

    @Override
    public Map<String,Object> getScore(Integer studentId) {
        List<Score> scores = scoreMapper.selectStudentScores(studentId);
        StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(studentId);
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score score1, Score score2) {
                return score2.getExamTime().compareTo(score1.getExamTime());
            }
        });

        List<Integer> point = new ArrayList<>();
        List<Integer> examIds = new ArrayList<>();
        List<String> examTitles = new ArrayList<>();
        for(Score score:scores) {
            point.add(score.getScore());
            examIds.add(score.getExamId());
        }
        for(Integer examId:examIds) {
            Exam exam = examMapper.selectByPrimaryKey(examId);
            examTitles.add(exam.getExamTitle());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("point",point);
        map.put("examTitle",examTitles);
        map.put("score",scores);
        map.put("studentInfo",studentInfo);
        return map;
    }
}
