
package com.authservice.config;

import com.authservice.model.Role;
import com.authservice.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserInfoUserDetails implements UserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean accountNonExpired;

    private boolean accountNonLocked ;

    private boolean credentialsNonExpired;

    private boolean enabled;
    private List<GrantedAuthority> authorities;
    private Set<Role> roles;

    public UserInfoUserDetails(User userInfo) {
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.roles = userInfo.getRoles();
        this.accountNonExpired=userInfo.isAccountNonExpired();
        this.accountNonLocked = userInfo.isAccountNonLocked();
        this.credentialsNonExpired=userInfo.isCredentialsNonExpired();
        this.enabled=userInfo.isEnabled();
        this.authorities = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

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
}

