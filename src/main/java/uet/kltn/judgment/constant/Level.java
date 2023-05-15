package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Level {
    LEVEL_PERSONAL(0, "Cá nhân"),
    LEVEL_ENTERPRISE(1, "Doanh nghiệp");


    private int id;
    private String name;

    Level(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Nullable
    public static Level getByName(String name) {
        for (Level level : values()) {
            if (level.name.equals(name)) {
                return level;
            }
        }
        return null;
    }
}
