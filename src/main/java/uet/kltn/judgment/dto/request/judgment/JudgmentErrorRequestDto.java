package uet.kltn.judgment.dto.request.judgment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JudgmentErrorRequestDto {
    @NotNull
    @JsonAlias("user_uid")
    private String userUid;

    @NotNull
    @JsonAlias("judgment_uid")
    private String judgmentUid;

    @NotNull
    @JsonAlias("error")
    private Integer error;

    @JsonAlias("error_content")
    private String errorContent;
}
