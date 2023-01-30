package uet.kltn.judgment.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotNull
    @NotEmpty
    @JsonAlias("username")
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 8, message = "password must be greater than 8")
    @Pattern(regexp = "[^\\s]+", message = "password include white space")
    @JsonAlias("password")
    private String password;

}
