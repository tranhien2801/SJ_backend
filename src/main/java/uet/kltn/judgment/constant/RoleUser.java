package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleUser {
    ROLE_MANAGER(0, "Quản lý"),
    ROLE_STAFF(1, "Nhân viên"),
    ROLE_ACCOUNTANT(2, "Kế toán"),
    ROLE_CONTENT(3, "Nhân viên content"),
    ROLE_OTHER(4, "Khác");

    private String name;
    private int id;

    RoleUser(int id, String roleName) {
        this.id = id;
        this.name = roleName;
    }

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
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
    public static RoleUser getById(int id) {
        for (RoleUser roleUser : values()) {
            if (roleUser.id == id) {
                return roleUser;
            }
        }
        return null;
    }

    @Nullable
    public static RoleUser getByName(String name) {
        for (RoleUser roleUser : values()) {
            if (roleUser.name.equals(name)) {
                return roleUser;
            }
        }
        return null;
    }
}

