package uet.kltn.judgment.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("role")
    private Integer role;
    @JsonProperty("name")
    private String name;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("description")
    private String description;
    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
