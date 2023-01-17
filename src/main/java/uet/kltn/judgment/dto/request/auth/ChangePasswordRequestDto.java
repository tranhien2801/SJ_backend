package uet.kltn.judgment.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.DtoField;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    @NotBlank
    @NotNull
    @Size(min = Constant.VALUE_DEFAULT_PASSWORD_SIZE, message = Constant.MSG_SIZE_GREATER + Constant.VALUE_DEFAULT_PASSWORD_SIZE)
    @Pattern(regexp = Constant.PATTERN_EXC_WHITE_SPACE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_INCLUDE_WHITE_SPACE)
    @JsonAlias({"currentPassword", "current_password"})
    private String currentPassword;

    @NotBlank
    @NotNull
    @Size(min = Constant.VALUE_DEFAULT_PASSWORD_SIZE, message = Constant.MSG_SIZE_GREATER + Constant.VALUE_DEFAULT_PASSWORD_SIZE)
    @Pattern(regexp = Constant.PATTERN_EXC_WHITE_SPACE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_INCLUDE_WHITE_SPACE)
    @JsonAlias({"newPassword", "new_password"})
    private String newPassword;

    @NotBlank
    @NotNull
    @Size(min = Constant.VALUE_DEFAULT_PASSWORD_SIZE, message = Constant.MSG_SIZE_GREATER + Constant.VALUE_DEFAULT_PASSWORD_SIZE)
    @Pattern(regexp = Constant.PATTERN_EXC_WHITE_SPACE, message = DtoField.PASSWORD_DTO_PASSWORD + Constant.MSG_INCLUDE_WHITE_SPACE)
    @JsonAlias({"reNewPassword", "re_new_password"})
    private String reNewPassword;
}
