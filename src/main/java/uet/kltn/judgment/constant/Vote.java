package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Vote {
    IS_VOTED(1, "Được bình chọn làm nguồn phát triển án lệ"),
    NO_IS_VOTED(0, "Không được bình chọn làm nguồn phát triển án lệ");

    private int id;
    private String name;

    private Vote(int id, String name) {
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
    public static Vote getById(int id) {
        for (Vote vote : values()) {
            if (vote.id == id) {
                return vote;
            }
        }
        return null;
    }

    @Nullable
    public static Vote getByName(String name) {
        for (Vote vote : values()) {
            if (vote.name.equals(name)) {
                return vote;
            }
        }
        return null;
    }
}
