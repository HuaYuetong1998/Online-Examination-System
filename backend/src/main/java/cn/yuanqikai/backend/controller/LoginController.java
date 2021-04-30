package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.UserLoginDTO;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.LoginService;
import cn.yuanqikai.backend.utils.CookieUtils;
import cn.yuanqikai.backend.utils.JwtUtils;
import cn.yuanqikai.backend.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public DataResponse login(@RequestBody LoginVO loginVO, HttpServletResponse response,
                                            @CookieValue(value = "token", required = false) String token) {
        // 获取token
        final String TOKEN = JwtUtils.getJwtToken(loginVO.getUserName(),loginVO.getPassword());
        if (token == null) {
            CookieUtils.writeCookie(response, "token", TOKEN);
        } else{
            logger.warn("token: " + token);
        }
        logger.warn(loginVO.toString());
        // 登录
        UserLoginDTO loginDTO = loginService.Login(loginVO);
        loginDTO.setToken(TOKEN);

        return DataResponse.success().data(loginDTO);
    }
}
