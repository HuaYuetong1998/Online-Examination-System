package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.vo.RegisterVO;

/**
 * @author yuanqikai
 * @date 2021-03-18 13:40
 */
public interface RegisterService {

    /**
     * 注册
     * @param registerVO
     */
    void register(RegisterVO registerVO);

    /**
     * 用户是否已存在
     * @param userName
     */
    void checkLoginName(String userName);
}
