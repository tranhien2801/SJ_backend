package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);
    private int id;

    private Gender(int id) {
        this.id = id;
    }

    @JsonValue
    public int getId() {
        return this.id;
    }

    @Nullable
    public static Gender getById(int id) {
        for (Gender gender : values()) {
            if (gender.id == id) {
                return gender;
            }
        }
        return null;
    }
}
