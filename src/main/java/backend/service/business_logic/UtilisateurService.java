package backend.service.business_logic;

import backend.dto.UserDtoPourList;
import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.dto.updateEntityRequest.UpdateAccountUser;
import backend.dto.updateEntityRequest.UpdateUserPassword;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UtilisateurService {

    Utilisateur getUtilisateurByEmail(String email);

    Utilisateur getUserByNom(String nom);

    boolean ajouter_user(NewUtilisateur user) throws MessagingException;

    boolean supprimer_user(String idUser);

    List<UserDtoPourList> getAll();

    String generatePassword();

    UserSignatures getUserSignaturesUrl(Role role);

    boolean verifyIfUserAlreadyExists(String email);

    boolean AddSignature(MultipartFile signature) throws Exception;

    Boolean updatePassword(UpdateUserPassword data);

    Boolean updateProfilePicture(MultipartFile photoProfile) throws Exception;

    Boolean updateUserInfo(UpdateAccountUser data);
}