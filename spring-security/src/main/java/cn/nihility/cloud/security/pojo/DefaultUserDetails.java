package cn.nihility.cloud.security.pojo;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultUserDetails implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = -7675958155156155708L;

    private String userName;
    private String password;
    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    public DefaultUserDetails(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public DefaultUserDetails(String userName, String password, List<GrantedAuthority> grantedAuthorities) {
        this.userName = userName;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
    public String toString() {
        return "{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
