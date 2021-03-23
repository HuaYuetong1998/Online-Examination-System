package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.response.BaseResponse;
import cn.yuanqikai.backend.service.RegisterService;
import cn.yuanqikai.backend.vo.RegisterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuanqikai
 * @date 2021-03-19 13:58
 */

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @PostMapping("/api/register")
    public BaseResponse register(@RequestBody RegisterVO registerVO) {
        // 打印注册用户注册日志
        logger.warn(registerVO.toString());
        registerService.register(registerVO);
        return BaseResponse.success();
    }

    /*@PostMapping("/api/register")
    public String hasExisted(@RequestParam(value = "hasExisted") String msg){

        return "";
    }*/
}
