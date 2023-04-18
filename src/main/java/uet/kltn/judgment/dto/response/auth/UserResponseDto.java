package uet.kltn.judgment.dto.response.auth;

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
    private String gender;

    @JsonProperty("birthday")
    private Date birthday;

    @JsonProperty("power")
    private String power;

    @JsonProperty("state")
    private String state;

    @JsonProperty("usage_time")
    private String usageTime;

    @JsonProperty("role")
    private String role;

    @JsonProperty("unit_name")
    private String unitName;

    @JsonProperty("work_name")
    private String workName;

    @JsonProperty("number_staff")
    private Integer numberStaff;

    @JsonProperty("case_type")
    private String caseType;

    @JsonProperty("function")
    private String function;

    @JsonProperty("last_login")
    private LocalDateTime lastLogin;

    @JsonProperty("level")
    private String level;
}
