package org.example.han.common.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
public class AccessUser implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    private Long id;

    private String loginId;

    private String userName;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public AccessUser(Long id, String loginId, String userName, List<String> roles) {

        roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .forEach(this.authorities::add);

        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Long getId() {
        return id;
    }
}
