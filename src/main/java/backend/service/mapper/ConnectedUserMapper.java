package backend.service.mapper;

import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import org.springframework.stereotype.Service;

@Service
public class ConnectedUserMapper {

    public ConnectedUser toConnectedUser(Utilisateur utilisateur) {
        return ConnectedUser.builder()
                .emailAsUsername(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .authorities(utilisateur.getRole().getGrantedAuthorities())
                .build();
    }

}