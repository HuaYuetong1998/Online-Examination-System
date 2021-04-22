package cn.yuanqikai.backend.service.impl;

import cn.yuanqikai.backend.dto.PaperDTO;
import cn.yuanqikai.backend.dto.QuestionDTO;
import cn.yuanqikai.backend.entity.ChoiceQuestion;
import cn.yuanqikai.backend.entity.Paper;
import cn.yuanqikai.backend.entity.PaperQuestionPool;
import cn.yuanqikai.backend.mapper.ChoiceQuestionMapper;
import cn.yuanqikai.backend.mapper.PaperMapper;
import cn.yuanqikai.backend.mapper.PaperQuestionPoolMapper;
import cn.yuanqikai.backend.service.PaperManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yuanqikai
 * @date 2021-04-16 14:08
 */

@Service
public class PaperManageServiceImpl implements PaperManageService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperQuestionPoolMapper paperQuestionPoolMapper;

    @Autowired
    private ChoiceQuestionMapper choiceQuestionMapper;

    @Override
    public int addPaper(PaperDTO paperDTO) {
        Paper paper = new Paper();
        if (paperDTO.getType() == 0) {
            paper.setChoiceNum(paperDTO.getNormalChoiceNum());
            paper.setFillNum(paperDTO.getNormalFillNum());
            paper.setJudgeNum(paperDTO.getNormalJudgeNum());
            paper.setSubjectiveNum(paperDTO.getNormalSubjectiveNum());
        } else if (paperDTO.getType() == 1) {
            paper.setChoiceNum(paperDTO.getChoiceNum());
            paper.setFillNum(paperDTO.getFillNum());
            paper.setJudgeNum(paperDTO.getJudgeNum());
            paper.setSubjectiveNum(paperDTO.getSubjectiveNum());
            paper.setChoiceScore(paperDTO.getChoiceScore());
            paper.setFillScore(paperDTO.getFillScore());
            paper.setJudgeScore(paperDTO.getJudgeScore());
            paper.setSubjectiveScore(paperDTO.getSubjectiveScore());
        }

        paper.setPaperTitle(paperDTO.getPaperTitle());
        paper.setTotalScore(paperDTO.getTotalScore());
        paper.setType(paperDTO.getType());
        paper.setStatus(1);
        Date today = new Date();
        paper.setCreateTime(today);
        paper.setUpdateTime(today);

        return paperMapper.insert(paper);
    }

    @Override
    public int countByPaperTitle(String paperTitle) {
        return paperMapper.countByPaperTitle(paperTitle);
    }

    @Override
    public void addPaperQuestionPool(PaperDTO paperDTO) {
        Paper paper = paperMapper.selectByPaperTitle(paperDTO.getPaperTitle());
        Integer paperId = paper.getPaperId();
        List<QuestionDTO> paperQuestion = paperDTO.getPaperQuestion();

        for (QuestionDTO questionDTO : paperQuestion) {
            PaperQuestionPool paperQuestionPool = new PaperQuestionPool();
            paperQuestionPool.setPaperId(paperId);
            paperQuestionPool.setQuestionId(questionDTO.getQuestionId());
            paperQuestionPoolMapper.insert(paperQuestionPool);
        }
    }

    @Override
    public Page<PaperDTO> selectMultiCondition(PaperDTO paperDTO) {
        PageHelper.startPage(paperDTO.getCurrentPage(), paperDTO.getPageSize());
        return paperMapper.selectMultiCondition(paperDTO);
    }

    @Override
    public Paper selectById(Integer paperId) {
        return paperMapper.selectByPrimaryKey(paperId);
    }

    @Override
    public List<Integer> selectPaperQuestionIds(Integer paperId) {
        return paperQuestionPoolMapper.selectPaperQuestionIds(paperId);
    }

    @Override
    public ChoiceQuestion selectQuestionDetail(Integer questionId) {
        return choiceQuestionMapper.selectQuestionDetail(questionId);
    }

    @Override
    public int updatePaper(PaperDTO paperDTO) {
        int choiceCount = 0;
        int fillCount = 0;
        int judgeCount = 0;
        int subjectiveCount = 0;
        int totalScore = 0;
        List<QuestionDTO> paperQuestion = paperDTO.getPaperQuestion();
        for (QuestionDTO questionDTO : paperQuestion) {
            Integer questionId = questionDTO.getQuestionId();
            if (questionId >= 100000 && questionId < 200000) {
                choiceCount++;
            } else if (questionId >= 200000 && questionId < 300000) {
                fillCount++;
            } else if (questionId >= 300000 && questionId < 400000) {
                judgeCount++;
            } else if (questionId >= 400000) {
                subjectiveCount++;
            }
            totalScore += questionDTO.getScore();
        }
        Paper paper = new Paper();
        paper.setPaperId(paperDTO.getPaperId());
        paper.setPaperTitle(paperDTO.getPaperTitle());
        paper.setType(0);
        paper.setStatus(paperDTO.getStatus());
        paper.setChoiceNum(choiceCount);
        paper.setFillNum(fillCount);
        paper.setJudgeNum(judgeCount);
        paper.setSubjectiveNum(subjectiveCount);
        paper.setTotalScore(totalScore);
        paper.setChoiceScore(paperDTO.getChoiceScore());
        paper.setFillScore(paperDTO.getFillScore());
        paper.setJudgeScore(paperDTO.getJudgeScore());
        paper.setSubjectiveScore(paperDTO.getSubjectiveScore());
        Date modifyDate = new Date();
        paper.setUpdateTime(modifyDate);
        return paperMapper.updateByPrimaryKeySelective(paper);
    }

    @Override
    public void updatePaperQuestionPool(PaperDTO paperDTO) {
        Integer paperId = paperDTO.getPaperId();
        int res = paperQuestionPoolMapper.deleteByPaperId(paperId);
        if(res != 0 ) {
            addPaperQuestionPool(paperDTO);
        }
    }

    @Override
    public int deletePaper(Integer paperId) {
        int res = paperQuestionPoolMapper.deleteByPaperId(paperId);
        if(res != 0) {
           return paperMapper.deleteByPrimaryKey(paperId);
        }
        return 0;
    }
}
