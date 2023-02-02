package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Level {
    LEVEL_PERSONAL(0),
    LEVEL_ENTERPRISE(1);


    private int id;

    Level(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public static Level getById(int id) {
        for (Level level : values()) {
            if (level.id == id) {
                return level;
            }
        }
        return null;
    }
}
