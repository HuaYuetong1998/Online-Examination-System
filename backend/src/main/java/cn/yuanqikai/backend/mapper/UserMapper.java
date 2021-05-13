package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.User;
import com.github.pagehelper.Page;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByUserName(String userName);

    User selectByPrimaryKey(Integer userId);

    Integer getUserCount(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Page<User> selectStudentUser();

    User selectByTel(String tel);
}