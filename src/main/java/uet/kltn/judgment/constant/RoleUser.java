package uet.kltn.judgment.constant;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleUser {
    ROLE(1),
    ROLE_OWNER(2),
    ROLE_MANAGER(3),
    ROLE_STAFF(4),
    ROLE_CONSUMER(5),
    ROLE_GROUP_ADMIN_OWNER(6),
    ROLE_GROUP_ADMIN_MANAGER(7);

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
}

