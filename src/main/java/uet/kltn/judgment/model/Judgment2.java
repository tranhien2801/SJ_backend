package uet.kltn.judgment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import uet.kltn.judgment.dto.request.judgment.UpdateJudgmentRequestDto;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`judgment`",
        indexes = {
                @Index(name = "index_uid", columnList = "uid"),
                @Index(name = "index_judgment_number", columnList = "judgment_number"),
                @Index(name = "index_judgment_content", columnList = "judgment_content"),
                @Index(name = "index_judgment_level", columnList = "judgment_level"),
                @Index(name = "index_type_document", columnList = "type_document"),
        })
@Where(clause = "state != 3")
public class Judgment2 extends BaseEntity {
    @Column(name = "`judgment_number`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String judgmentNumber;

    @Column(name = "`judgment_name`", columnDefinition = "TEXT", nullable = false)
    private String judgmentName;

    @Column(name = "`type_document`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String typeDocument;

    @Column(name = "`judgment_level`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String judgmentLevel;

    @Column(name = "`judgment_content`", columnDefinition = "LONGTEXT", nullable = false)
    private String judgmentContent;

    @Column(name = "`judgment_text`", columnDefinition = "LONGTEXT", nullable = false)
    private String judgmentText;

    @Column(name = "`judgment_summarization`", columnDefinition = "LONGTEXT", nullable = true)
    private String judgmentSummarization;

    @Column(name = "`date_issued`", columnDefinition = "DATE", nullable = false)
    private Date dateIssued;

    @Column(name = "`date_upload`", columnDefinition = "DATE", nullable = false)
    private Date dateUpload;

    @Column(name = "`url`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String url;

    @Column(name = "`file_download`", columnDefinition = "VARCHAR(5000)", nullable = false)
    private String fileDownload;

    @Column(name = "`pdf_viewer`", columnDefinition = "VARCHAR(5000)", nullable = false)
    private String pdfViewer;

    @Column(name = "`corrections`", columnDefinition = "INT", nullable = false)
    private int corrections;

    @Column(name = "`count_vote`", columnDefinition = "INT", nullable = false)
    private int countVote;

    @Column(name = "`count_eyes`", columnDefinition = "INT", nullable = false)
    private int countEyes;

    @Column(name = "`count_download`", columnDefinition = "INT", nullable = false)
    private int countDownload;

    @Column(name = "`precedent`", columnDefinition = "TINYINT(10) DEFAULT 0", nullable = true)
    private int precedent;

    @ManyToOne
    @JoinColumn(name = "`court_uid`")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "`case_uid`")
    private Case aCase;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "judgments", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users;

    public void update(UpdateJudgmentRequestDto updateJudgmentRequestDto) {
        this.setModified(LocalDateTime.now());
        if (updateJudgmentRequestDto.getJudgmentNumber() != null)
            this.judgmentNumber = updateJudgmentRequestDto.getJudgmentNumber();
        if (updateJudgmentRequestDto.getJudgmentName() != null)
            this.judgmentName = updateJudgmentRequestDto.getJudgmentName();
        if (updateJudgmentRequestDto.getJudgmentContent() != null)
            this.judgmentContent = updateJudgmentRequestDto.getJudgmentContent();
        if (updateJudgmentRequestDto.getJudgmentText() != null)
            this.judgmentText = updateJudgmentRequestDto.getJudgmentText();
        if (updateJudgmentRequestDto.getUrl() != null)
            this.url = updateJudgmentRequestDto.getUrl();
        if (updateJudgmentRequestDto.getFileDownload() != null)
            this.fileDownload = updateJudgmentRequestDto.getFileDownload();
        if (updateJudgmentRequestDto.getCountEyes() != null)
            this.countEyes = updateJudgmentRequestDto.getCountEyes();
        if (updateJudgmentRequestDto.getCountDownload() != null)
            this.countDownload = updateJudgmentRequestDto.getCountDownload();
    }
}
