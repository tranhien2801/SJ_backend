package uet.kltn.judgment.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.DtoField;

import javax.validation.constraints.*;


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

    @NotBlank @NotNull
    @Size(min = Constant.VALUE_DEFAULT_PASSWORD_SIZE, message = Constant.MSG_SIZE_GREATER + Constant.VALUE_DEFAULT_PASSWORD_SIZE)
    @Pattern(regexp = Constant.PATTERN_EXC_WHITE_SPACE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_INCLUDE_WHITE_SPACE)
    @JsonAlias("password")
    private String password;

    @Size(max = 16, message = "phone_number must be greater than 8")
    @JsonAlias({"phoneNumber", "phone_number"})
    private String phoneNumber;

    @Max(4)
    @Min(4)
    @JsonProperty("role")
    private int role;

    @JsonProperty("description")
    private String description;

}
