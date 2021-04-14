package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-04-09 17:54
 */
public class SearchQuestionDTO {

    private Integer questionId;

    private Integer subjectId;

    private String question;

    private String questionType;

    private Integer PageSize;

    private Integer CurrentPage;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        CurrentPage = currentPage;
    }
}
