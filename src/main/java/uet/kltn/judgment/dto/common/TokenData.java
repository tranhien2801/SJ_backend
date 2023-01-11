package uet.kltn.judgment.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenData {
    private String userUid;
    private int isOtp = 0;
    private Integer role;

    public TokenData(String userUid, Integer role){
        this.userUid = userUid;
        this.role = role;
    }
}
