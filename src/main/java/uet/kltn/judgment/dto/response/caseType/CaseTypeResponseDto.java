package uet.kltn.judgment.dto.response.caseType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaseTypeResponseDto {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("value")
    private String value;

    @JsonProperty("state")
    private Integer state;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("modified")
    private LocalDateTime modified;

}
