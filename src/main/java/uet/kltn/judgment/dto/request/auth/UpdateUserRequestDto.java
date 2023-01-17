package uet.kltn.judgment.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    @NotNull
    @NotBlank
    @JsonAlias("name")
    private String name;

    @Email
    @JsonAlias("email")
    private String email;

    @Size(max = 16, min = 9, message = "phone_number must be greater than 8")
    @JsonAlias({"phoneNumber", "phone_number"})
    private String phoneNumber;

    @JsonProperty("description")
    private String description;

    @Max(4)
    @Min(3)
    @JsonProperty("role")
    private int role;

}
