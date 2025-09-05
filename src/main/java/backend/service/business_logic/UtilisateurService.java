package backend.service.business_logic;

import backend.dto.UserDtoPourList;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.dto.updateEntityRequest.UpdateAccountUser;
import backend.model.Utilisateur;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UtilisateurService {

    Utilisateur getUtilisateurByEmail(String email);

    boolean ajouter_user(NewUtilisateur user) throws MessagingException;

    boolean supprimer_user(String idUser);

    List<UserDtoPourList> getAll();

    boolean update_account(UpdateAccountUser updateAccountUser);

    String generatePassword();
}