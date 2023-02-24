package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum UsageTime {
    SEVEN_DAYS_TRIAL(0, "7 ngày dùng thử"),
    FOREVER(1, "vĩnh viễn");

    private int id;
    private String name;

    private UsageTime(int id, String name) {
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
    public static UsageTime getById(int id) {
        for (UsageTime usageTime : values()) {
            if (usageTime.id == id) {
                return usageTime;
            }
        }
        return null;
    }

    @Nullable
    public static UsageTime getByName(String name) {
        for (UsageTime usageTime : values()) {
            if (usageTime.name.equals(name)) {
                return usageTime;
            }
        }
        return null;
    }
}
