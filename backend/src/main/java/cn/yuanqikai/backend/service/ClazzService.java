package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.entity.Clazz;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-23 13:19
 */
public interface ClazzService {

    int addClazz(Clazz clazz);

    int findRepeat(Clazz clazz);

    List<Clazz> selectAll();

    Page<Clazz> selectMultiple(Clazz clazz);

    Clazz selectById(Integer classId);

    int deleteById(Integer classId);

    int updateClazz(Clazz clazz);
}
