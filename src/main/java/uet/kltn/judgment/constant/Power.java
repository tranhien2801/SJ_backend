package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Power {
    SYSTEM_ADMIN(1),
    ADMIN(2),
    MANAGER(3),
    STAFF(4);

    @Nullable
    public static Power getById(int id) {
        for (Power status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return null;
    }
    private int id;

    Power(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

}
