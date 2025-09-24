package FutureCraft.tikitaka.back_end.userDetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
    }

    @Override
    public String getPassword() {
       return null;
    }

    @Override
    public String getUsername() {
       return userId;
    }
    
}
