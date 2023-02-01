package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleUser {
    ROLE_MANAGER(0),
    ROLE_STAFF(1),
    ROLE_ACCOUNTANT(2),
    ROLE_CONTENT(3),
    ROLE_OTHER(4);

    private int id;

    RoleUser(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public static RoleUser getById(int id) {
        for (RoleUser roleUser : values()) {
            if (roleUser.id == id) {
                return roleUser;
            }
        }
        return null;
    }
}

