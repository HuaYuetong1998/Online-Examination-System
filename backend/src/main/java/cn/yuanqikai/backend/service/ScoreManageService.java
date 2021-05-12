package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.entity.Score;

import java.util.List;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-05-11 15:23
 */
public interface ScoreManageService {

    int saveScore(Score score);

    Map<String,Object> getScore(Integer studentId);
}
