package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.entity.Clazz;
import cn.yuanqikai.backend.mapper.ClazzMapper;
import cn.yuanqikai.backend.service.ClazzService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-23 13:19
 */

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public int addClazz(Clazz clazz) {
        return clazzMapper.insert(clazz);
    }

    @Override
    public int findRepeat(Clazz clazz) {
        return clazzMapper.findRepeat(clazz);
    }

    @Override
    public List<Clazz> selectAll() {
        return clazzMapper.selectAll();
    }

    @Override
    public Page<Clazz> selectMultiple(Clazz clazz) {
        PageHelper.startPage(clazz.getCurrentPage(),clazz.getPageSize());
        return clazzMapper.selectMultiple(clazz);
    }

    @Override
    public Clazz selectById(Integer classId) {
        return clazzMapper.selectByPrimaryKey(classId);
    }

    @Override
    public int deleteById(Integer classId) {
        return clazzMapper.deleteByPrimaryKey(classId);
    }

    @Override
    public int updateClazz(Clazz clazz) {
        return clazzMapper.updateByPrimaryKeySelective(clazz);
    }
}
