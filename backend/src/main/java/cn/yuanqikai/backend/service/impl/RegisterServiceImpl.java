package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.mapper.UserMapper;
import cn.yuanqikai.backend.entity.User;
import cn.yuanqikai.backend.exception.UserException;
import cn.yuanqikai.backend.exception.enums.UserEnum;
import cn.yuanqikai.backend.service.RegisterService;
import cn.yuanqikai.backend.vo.RegisterVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yuanqikai
 * @date 2021-03-18 13:40
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(RegisterVO registerVO) {
        registerVO.valid();

        String userName = registerVO.getUserName();
        String password = registerVO.getPassword();
        String realName = registerVO.getRealName();
        String tel = registerVO.getTel();
        String email = registerVO.getEmail();
        String gender = registerVO.getGender();
        Integer role = registerVO.getRole();

        // 在前端进行sha256加密
        //String shaPassword = SHACodeUtils.getSHA256(password);
        checkLoginName(userName);

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRealName(realName);
        user.setTel(tel);
        user.setEmail(email);
        user.setGender(gender);
        user.setRole(role);

        Date today = new Date();
        user.setCreateTime(today);
        user.setUpdateTime(today);

        userMapper.insert(user);
    }

    @Override
    public void checkLoginName(String userName) {
        try{
            if (StringUtils.isBlank(userName)) {
                throw new UserException(UserEnum.USER_NAME_BLANK);
            }
        }catch (UserException e) {
            e.printStackTrace();
        }

        try{
            if (userMapper.getUserCount(userName).equals(1)) {
                throw new UserException(UserEnum.USER_HAS_EXISTED);
            }
        }catch (UserException e) {
            e.printStackTrace();
        }
    }
}
