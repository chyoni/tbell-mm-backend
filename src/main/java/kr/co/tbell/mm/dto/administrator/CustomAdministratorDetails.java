package kr.co.tbell.mm.dto.administrator;

import kr.co.tbell.mm.entity.administrator.Administrator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomAdministratorDetails implements UserDetails {

    private final Administrator administrator;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return administrator.getRole().getDescription();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return administrator.getPassword();
    }

    @Override
    public String getUsername() {
        return administrator.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return administrator.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return administrator.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return administrator.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return administrator.isEnabled();
    }
}
