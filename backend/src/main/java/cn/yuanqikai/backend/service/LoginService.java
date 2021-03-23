package cn.yuanqikai.backend.service;
import cn.yuanqikai.backend.dto.UserLoginDTO;
import cn.yuanqikai.backend.vo.LoginVO;

/**
 * @author yuanqikai
 * @date 2021-03-18 11:28
 */
public interface LoginService {

    /**
     * 登录
     * @param loginVO
     */
    UserLoginDTO Login(LoginVO loginVO);
}
