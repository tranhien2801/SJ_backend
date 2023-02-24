package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Gender {
    MALE(0, "Nam"),
    FEMALE(1, "Nữ"),
    OTHER(2, "Khác");
    private int id;
    private String name;

    private Gender(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public int getId() {
        return this.id;
    }

    @JsonValue
    public String getName() {
        return this.name;
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

    @Nullable
    public static Gender getByName(String name) {
        for (Gender gender : values()) {
            if (gender.name.equals(name)) {
                return gender;
            }
        }
        return null;
    }
}
