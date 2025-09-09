package backend.service.utils;

import backend.model.Utilisateur;
import backend.service.business_logic.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConnectedUserGetter {

    private final UtilisateurService utilisateurService;

    public Utilisateur getConnectedUser(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return utilisateurService.getUtilisateurByEmail(userEmail);
    }

}
