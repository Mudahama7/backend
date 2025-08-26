package backend.model.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
public class ConnectedUser implements UserDetails {

    private String emailAsUsername;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return this.emailAsUsername;
    }

}