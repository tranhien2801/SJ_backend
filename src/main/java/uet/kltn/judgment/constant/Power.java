package uet.kltn.judgment.constant;

import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Power {
    SYSTEM_ADMIN(1, "Quản trị hệ thống"),
    ADMIN(2, "Quản trị viên"),
    MANAGER(3, "Quản lý"),
    STAFF(4, "Nhân viên");

    private int id;
    private String name;

    @Nullable
    public static Power getById(int id) {
        for (Power status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return null;
    }

    @Nullable
    public static Power getByName(String name) {
        for (Power status : values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }


    Power(int id, String name) {
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
    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

}
