package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.dto.PaperDTO;
import cn.yuanqikai.backend.entity.Paper;
import com.github.pagehelper.Page;

import java.util.List;

public interface PaperMapper {
    int deleteByPrimaryKey(Integer paperId);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectByPrimaryKey(Integer paperId);

    int countByPaperTitle(String paperTitle);

    Paper selectByPaperTitle(String paperTitle);

    Page<PaperDTO> selectMultiCondition(PaperDTO paperDTO);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    List<Paper> selectAllPaper();
}