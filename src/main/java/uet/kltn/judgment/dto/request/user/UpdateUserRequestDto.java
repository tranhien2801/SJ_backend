package uet.kltn.judgment.dto.request.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {

    @JsonAlias("name")
    private String name;

    @Email
    @JsonAlias("email")
    private String email;

    @JsonAlias("level")
    private String level;

    @JsonAlias("state")
    private String state;

    @JsonAlias("usage_time")
    private String usageTime;

    @JsonAlias("functions")
    private Set<String> functions;

    @JsonAlias("case_types")
    private Set<String> caseTypes;

    @Size(max = 16, min = 9, message = "phone_number must be greater than 8")
    @JsonAlias({"phoneNumber", "phone_number"})
    private String phoneNumber;

    @JsonAlias("unit")
    private String unit;

    @JsonAlias("role")
    private String role;

    @JsonAlias("work")
    private String work;

    @JsonAlias("number_employee")
    private Integer numberEmployee;

    @JsonAlias("description")
    private String description;

}
