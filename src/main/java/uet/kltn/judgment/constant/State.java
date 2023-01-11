package uet.kltn.judgment.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum State {
    ACTIVE(1),
    INACTIVE(2),
    DELETE(3),
    PROCESSED(4);

    private int id;

    private State(int id) {
        this.id = id;
    }

    @JsonValue
    public int getId() {
        return this.id;
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
}
