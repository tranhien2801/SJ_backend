package uet.kltn.judgment.dto.response.judgment;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataResponseDto {
    @JsonProperty("total_judgments")
    private Integer totalJudgments;

    @JsonProperty("total_users")
    private Integer totalUsers;

    @JsonProperty("total_managers")
    private Integer totalManagers;

    @JsonProperty("date_latest")
    private Date dateLastest;

    @JsonProperty("date_newest")
    private Date dateNewest;
}
