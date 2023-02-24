package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum State {
    ACTIVE(1, "Hoạt động"),
    INACTIVE(2, "Không hoạt động"),
    DELETE(3, "Đã xóa"),
    PROCESSED(4, "Đã xử lý");

    private int id;
    private String name;

    private State(int id, String name) {
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
    public static State getById(int id) {
        for (State status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return null;
    }

    @Nullable
    public static State getByName(String name) {
        for (State status : values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }
}
