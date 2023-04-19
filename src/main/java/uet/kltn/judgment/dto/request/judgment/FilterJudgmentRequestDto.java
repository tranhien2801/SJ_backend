package uet.kltn.judgment.dto.request.judgment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterJudgmentRequestDto {
    @JsonAlias("judgment_content")
    private String judgmentNumber;

    @JsonAlias("court_level")
    private String courtLevel;

    @JsonAlias("judgment_level")
    private String judgmentLevel;

    @JsonAlias("type_document")
    private String typeDocument;

    @JsonAlias("case_type")
    private String caseType;

    @JsonAlias("date_from")
    private Date dateFrom;

    @JsonAlias("date_to")
    private Date dateTo;

    @JsonAlias("vote")
    private Boolean vote;

    @JsonAlias("precedent")
    private Boolean precedent;

    public boolean isEmpty() {
        return judgmentNumber == null && courtLevel == null && judgmentLevel == null && typeDocument == null
                && caseType == null && dateFrom == null && dateTo == null && vote == null && precedent == null;
    }

}

