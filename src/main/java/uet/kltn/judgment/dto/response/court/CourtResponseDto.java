package uet.kltn.judgment.dto.response.court;

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
public class CourtResponseDto {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("court_level")
    private String courtLevel;

    @JsonProperty("address")
    private String address;

    @JsonProperty("value")
    private String courtName;

    @JsonProperty("state")
    private Integer state;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("modified")
    private LocalDateTime modified;

}
