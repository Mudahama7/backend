package backend.service.mapper;

import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ConnectedUserMapper {

    public UserDetails toConnectedUser(Utilisateur utilisateur) {
        return ConnectedUser.builder()
                .authorities(utilisateur.getRole().getGrantedAuthorities())
                .emailAsUsername(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .build();
    }

}
