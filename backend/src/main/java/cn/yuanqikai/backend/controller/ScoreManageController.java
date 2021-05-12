package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.entity.Score;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.ScoreManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-05-11 15:24
 */
@RestController
public class ScoreManageController {
    @Autowired
    private ScoreManageService scoreManageService;

    @PostMapping("/api/score/save")
    public DataResponse saveScore(@RequestBody Score score) {
        int res = scoreManageService.saveScore(score);
        if(res > 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @GetMapping("/api/score/getRecently")
    public DataResponse getRecent(@RequestParam Integer studentId) {
        Map<String, Object> Score = scoreManageService.getScore(studentId);
        return DataResponse.success().data(Score);
    }
}
