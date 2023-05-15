package uet.kltn.judgment.dto.request.crawler;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateCrawl {
    @NotNull
    @JsonAlias("date_from")
    private String date_from;

    @NotNull
    @JsonAlias("date_to")
    private String date_to;

    @Override
    public String toString() {
        return "date_from: " + date_from;
    }

}
