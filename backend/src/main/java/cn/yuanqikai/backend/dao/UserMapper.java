package cn.yuanqikai.backend.dao;

import cn.yuanqikai.backend.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    int getUserCount(String userName);

    User selectByUserName(String userName);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}