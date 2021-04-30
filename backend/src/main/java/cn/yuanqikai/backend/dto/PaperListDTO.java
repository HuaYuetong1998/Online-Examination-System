package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-04-26 09:34
 */
public class PaperListDTO {

    private String paperTitle;

    private Integer paperId;

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }
}
