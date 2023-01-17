package uet.kltn.judgment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("gender")
    private Integer gender;

    @JsonProperty("birthday")
    private Date birthday;

    @JsonProperty("power")
    private int power;

    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
}
