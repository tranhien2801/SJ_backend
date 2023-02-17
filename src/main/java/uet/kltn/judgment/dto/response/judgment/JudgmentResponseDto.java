package uet.kltn.judgment.dto.response.judgment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JudgmentResponseDto {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("judgment_number")
    private String judgmentNumber;

    @JsonProperty("judgment_name")
    private String judgmentName;

    @JsonProperty("type_document")
    private String typeDocument;

    @JsonProperty("judgment_level")
    private String judgmentLevel;

    @JsonProperty("court_name")
    private String courtName;

    @JsonProperty("case_name")
    private String caseName;

    @JsonProperty("case_type")
    private String caseType;

    @JsonProperty("judgment_content")
    private String judgmentContent;

    @JsonProperty("judgment_text")
    private String judgmentText;

    @JsonProperty("date_issued")
    private Date dateIssued;

    @JsonProperty("date_upload")
    private Date dateUpload;

    @JsonProperty("url")
    private String url;

    @JsonProperty("file_download")
    private String fileDownload;

    @JsonProperty("pdf_viewer")
    private String pdfViewer;

    @JsonProperty("count_vote")
    private Integer countVote;

    @JsonProperty("count_eyes")
    private Integer countEyes;

    @JsonProperty("count_download")
    private Integer countDownload;
}
