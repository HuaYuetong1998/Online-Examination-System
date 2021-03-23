package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.service.RegisterService;
import cn.yuanqikai.backend.vo.RegisterVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanqikai
 * @date 2021-03-22 10:22
 */
@RestController
@SpringBootTest
public class RegisterControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Test
    void RegisterTest() {
        logger.info("test");
    }
}
