package backend.service.contract;

import backend.dto.NewUserRequest;
import backend.model.Utilisateur;
import jakarta.mail.MessagingException;

public interface UserService {

    Utilisateur findByEmail(String email);

    boolean ajouter_user(NewUserRequest user) throws MessagingException;

    boolean supprimer_user(String idUser);

}