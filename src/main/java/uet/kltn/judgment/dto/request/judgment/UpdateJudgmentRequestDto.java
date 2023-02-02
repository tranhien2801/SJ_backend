package uet.kltn.judgment.dto.request.judgment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJudgmentRequestDto {
    @JsonProperty("judgment_number")
    private String judgmentNumber;

    @JsonProperty("judgment_name")
    private String judgmentName;

    @JsonProperty("judgment_content")
    private String judgmentContent;

    @JsonProperty("judgment_text")
    private String judgmentText;

    @JsonProperty("url")
    private String url;

    @JsonProperty("file_download")
    private String fileDownload;

    @JsonProperty("count_eyes")
    private Integer countEyes;

    @JsonProperty("count_download")
    private Integer countDownload;
}
