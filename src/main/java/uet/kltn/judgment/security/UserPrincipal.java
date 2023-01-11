package uet.kltn.judgment.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uet.kltn.judgment.model.User;

import java.util.Collection;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private String id;
    private String email;
    private String password;
    private User user;
    private Integer shopId;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private int isOtp = 0;

//    public UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities,
//                         User user) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.authorities = authorities;
//        this.user = user;
//    }

//    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = null;
//        if (user.getPower() == Power.ADMIN.getId()){
//            authorities = Collections.
//                    singletonList(new SimpleGrantedAuthority("ADMIN"));
//        } else if (user.getPower() == Power.SYSTEM_ADMIN.getId()){
//            authorities = Collections.
//                    singletonList(new SimpleGrantedAuthority("SYSTEM_ADMIN"));
//        } else if (user.getPower() == Power.MANAGER.getId()){
//            authorities = Collections.
//                    singletonList(new SimpleGrantedAuthority("MANAGER"));
//        } else if (user.getPower() == Power.STAFF.getId()){
//            authorities = Collections.
//                    singletonList(new SimpleGrantedAuthority("STAFF"));
//        }
//        return new UserPrincipal(
//                user.getUid(),
//                user.getEmail(),
//                user.getPassword(),
//                authorities,
//                user
//        );
//    }

//    public static UserPrincipal create(User user, Map<String, Object> attributes) {
//        UserPrincipal userPrincipal = UserPrincipal.create(user);
//        userPrincipal.setAttributes(attributes);
//        return userPrincipal;
//    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
