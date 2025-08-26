package backend.service.contract;

import backend.dto.newEntityRequest.NewUser;
import backend.model.Utilisateur;
import jakarta.mail.MessagingException;

public interface UserService {

    Utilisateur findByEmail(String email);

    boolean ajouter_user(NewUser user) throws MessagingException;

    boolean supprimer_user(String idUser);

    String generatePassword();
}