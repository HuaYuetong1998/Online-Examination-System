package cn.yuanqikai.backend.service;

import cn.yuanqikai.backend.dto.PaperDTO;
import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.Paper;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-16 14:08
 */
public interface PaperManageService {

    int addPaper(PaperDTO paperDTO);

    int countByPaperTitle(String paperTitle);

    void addPaperQuestionPool(PaperDTO paperDTO);

    Page<PaperDTO> selectMultiCondition(PaperDTO paperDTO);

    Paper selectById(Integer paperId);

    List<Integer> selectPaperQuestionIds(Integer paperId);

    ChoiceQuestion selectQuestionDetail(Integer questionId);

    int updatePaper(PaperDTO paperDTO);

    void updatePaperQuestionPool(PaperDTO paperDTO);

    int deletePaper(Integer paperId);
}
