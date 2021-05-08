package cn.yuanqikai.backend.mapper;

import cn.yuanqikai.backend.entity.AnswerSheet;
import org.apache.ibatis.annotations.Param;

public interface AnswerSheetMapper {
    int insert(AnswerSheet record);

    int insertSelective(AnswerSheet record);

    int selectRepeat(@Param("examId") Integer examId,@Param("studentId") Integer studentId);

    int updateAnswerSheet(AnswerSheet answerSheet);
}