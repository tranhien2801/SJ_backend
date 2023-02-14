package uet.kltn.judgment.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.DtoField;
import uet.kltn.judgment.model.Function;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotNull @NotBlank
    @JsonAlias("name")
    private String name;

    @Email
    @NotNull @NotBlank
    @JsonAlias("email")
    private String email;

    @NotBlank @NotNull(message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_NOT_BLANK)
    @Size(min = Constant.VALUE_DEFAULT_PASSWORD_SIZE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_SIZE_GREATER + Constant.VALUE_DEFAULT_PASSWORD_SIZE)
    @Pattern(regexp = Constant.PATTERN_EXC_WHITE_SPACE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_NOT_INCLUDE_WHITE_SPACE)
    @JsonAlias("password")
    private String password;

    @JsonAlias("level")
    private Integer level;

    @JsonAlias("state")
    private Integer state;

    @JsonAlias("usage_time")
    private Integer usageTime;

    @JsonAlias("functions")
    private Set<String> functions;

    @JsonAlias("case_types")
    private Set<String> caseTypes;

    @Size(max = 16, message = "phone_number phải có độ dài lớn hơn 8")
    @JsonAlias({"phoneNumber", "phone_number"})
    private String phoneNumber;

    @JsonAlias("unit")
    private String unit;

    @NotNull
    @JsonAlias("role")
    private Integer role;

    @JsonAlias("work")
    private String work;

    @JsonAlias("number_employee")
    private Integer numberEmployee;

    @JsonAlias("power")
    private Integer power;

    @JsonAlias("description")
    private String description;

}
