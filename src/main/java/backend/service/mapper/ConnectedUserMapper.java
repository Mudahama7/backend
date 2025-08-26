package backend.service.mapper;

import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import backend.service.contract.UserService;
import org.springframework.stereotype.Service;

@Service
public class ConnectedUserMapper {

    private UserService userService;

    public ConnectedUser fromEmailToConnectedUserObjet(String userEmail) {
        Utilisateur utilisateur = userService.findByEmail(userEmail);
        return ConnectedUser.builder()
                .emailAsUsername(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .authorities(utilisateur.getRole().getGrantedAuthorities())
                .build();
    }

}
