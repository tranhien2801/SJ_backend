package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Precedent {
    APPLYING_PRECEDENT(1, "Áp dụng án lệ"),
    NO_APPLYING_PRECEDENT(0, "Không áp dụng án lệ");

    private int id;
    private String name;

    private Precedent(int id, String name) {
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
    public static Precedent getById(int id) {
        for (Precedent precedent : values()) {
            if (precedent.id == id) {
                return precedent;
            }
        }
        return null;
    }

    @Nullable
    public static Precedent getByName(String name) {
        for (Precedent precedent : values()) {
            if (precedent.name.equals(name)) {
                return precedent;
            }
        }
        return null;
    }
}
