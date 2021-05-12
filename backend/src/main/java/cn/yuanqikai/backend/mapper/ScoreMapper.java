package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.Score;

import java.util.List;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer scoreId);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer scoreId);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);

    List<Score> selectStudentScores(Integer studentId);
}