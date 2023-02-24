package uet.kltn.judgment.dto.request.judgment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedJudgmentRequestDto {
    @JsonAlias("user_uid")
    private String userUid;

    @JsonAlias("judgment_uid")
    private String judgmentUid;

}
