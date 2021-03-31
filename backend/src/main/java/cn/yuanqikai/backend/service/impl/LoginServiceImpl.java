package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.dao.UserMapper;
import cn.yuanqikai.backend.dto.UserLoginDTO;
import cn.yuanqikai.backend.entity.User;
import cn.yuanqikai.backend.exception.UserException;
import cn.yuanqikai.backend.exception.enums.UserEnum;
import cn.yuanqikai.backend.service.LoginService;
import cn.yuanqikai.backend.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * @author yuanqikai
 * @date 2021-03-18 11:28
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginDTO Login(LoginVO loginVO) {
        loginVO.valid();

        String userName = loginVO.getUserName();
        String password = loginVO.getPassword();
        //在前端进行sha256加密
        //String shaPassword = SHACodeUtils.getSHA256(password);

        User user = userMapper.selectByUserName(userName);
        if (user == null) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        if (user.getPassword().equals(password)) {
            userLoginDTO.setUserId(user.getUserId());
            userLoginDTO.setUserName(user.getUserName());
            userLoginDTO.setPassword(user.getPassword());
            userLoginDTO.setRealName(user.getRealName());
            userLoginDTO.setRole(user.getRole());
            return userLoginDTO;
        }else {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
    }
}
