package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.Clazz;
import com.github.pagehelper.Page;

import java.util.List;

public interface ClazzMapper {
    int deleteByPrimaryKey(Integer classId);

    int insert(Clazz record);

    int insertSelective(Clazz record);

    Clazz selectByPrimaryKey(Integer classId);

    int updateByPrimaryKeySelective(Clazz record);

    int updateByPrimaryKey(Clazz record);

    int findRepeat(Clazz clazz);

    Page<Clazz> selectMultiple(Clazz clazz);

    List<Clazz> selectAll();
}