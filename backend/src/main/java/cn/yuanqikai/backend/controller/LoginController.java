package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.response.BaseResponse;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.LoginService;
import cn.yuanqikai.backend.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuanqikai
 * @date 2021-03-18 11:25
 */

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping("/api/login")
    public DataResponse login(@RequestBody LoginVO loginVO) {

        logger.warn(loginVO.toString());
        // 登录
        loginService.Login(loginVO);
        return DataResponse.success();
    }
}
