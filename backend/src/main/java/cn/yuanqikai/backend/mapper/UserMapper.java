package cn.yuanqikai.backend.dao;

import cn.yuanqikai.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByUserName(String userName);

    User selectByPrimaryKey(Integer userId);

    Integer getUserCount(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}