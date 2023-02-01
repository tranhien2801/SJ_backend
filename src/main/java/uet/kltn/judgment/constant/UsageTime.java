package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum UsageTime {
    SEVEN_DAYS_TRIAL(0),
    FOREVER(1);

    private int id;

    private UsageTime(int id) {
        this.id = id;
    }

    @JsonValue
    public int getId() {
        return this.id;
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
}
