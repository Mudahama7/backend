package backend.controller;

import backend.dto.UserDtoPourList;
import backend.dto.UserProfile;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.dto.updateEntityRequest.UpdateAccountUser;
import backend.dto.updateEntityRequest.UpdateUserPassword;
import backend.exception.type_exception.AddInvalidUserException;
import backend.exception.type_exception.UserNotFoundException;
import backend.model.Utilisateur;
import backend.service.business_logic.UtilisateurService;
import backend.service.utils.ConnectedUserGetter;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/utilisateur/")
public class UserController {

    private final UtilisateurService utilisateurService;
    private final ConnectedUserGetter connectedUserGetter;

    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    @PostMapping("ajouter_utilisateur/")
    public ResponseEntity<?> ajouterUtilisateur(@RequestBody NewUtilisateur newUtilisateur) throws MessagingException {
        if (utilisateurService.verifyIfUserAlreadyExists(newUtilisateur.getEmail())) {
            throw new AddInvalidUserException("Cette adresse mail est déjà attribuée à un autre compte ! ");
        }
        return ResponseEntity.ok(utilisateurService.ajouter_user(newUtilisateur));
    }


    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    @DeleteMapping("supprimer_utilisateur/{idUser}/")
    public ResponseEntity<Boolean> supprimerUtilisateur(@PathVariable("idUser") String idUser) {
        boolean response = utilisateurService.supprimer_user(idUser);
        if (!response) {
            throw new UserNotFoundException("L'utilisateur avec l'id "+idUser+" n'existe pas");
        }
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('consulter_utilisateur')")
    @GetMapping("get_all/")
    public ResponseEntity<List<UserDtoPourList>> findAll() {
        List<UserDtoPourList> userList = utilisateurService.getAll();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("Aucun utilisateur trouvé, à part l'admin personne d'autre n'a encore un compte !");
        }
        return ResponseEntity.ok(userList);
    }

    @PreAuthorize("hasAuthority('modifier_profil')")
    @PutMapping("update_signature/")
    public ResponseEntity<Boolean> updateSignature(
            @RequestParam MultipartFile signature
            ) throws Exception {
        return ResponseEntity.ok(utilisateurService.AddSignature(signature));
    }

    @PreAuthorize("hasAuthority('modifier_profil')")
    @PutMapping("update_passwprd/")
    public ResponseEntity<Boolean> updatePassword(UpdateUserPassword data){
        return ResponseEntity.ok(utilisateurService.updatePassword(data));
    }

    @PreAuthorize("hasAuthority('modifier_profil')")
    @PutMapping("update_photo/")
    public ResponseEntity<Boolean> updateProfilePicture(
            @RequestParam MultipartFile photo
    ) throws Exception {
        return ResponseEntity.ok(utilisateurService.updateProfilePicture(photo));
    }


    @PreAuthorize("hasAuthority('modifier_profil')")
    @GetMapping("profile/")
    public ResponseEntity<UserProfile> getProfile(){

        Utilisateur connectedUser = connectedUserGetter.getConnectedUser();

        return ResponseEntity.ok(
                UserProfile.builder()
                        .photoProfil(connectedUser.getPhotoProfilUrl())
                        .build()
        );

    }


    @PreAuthorize("hasAuthority('modifier_profil')")
    @PutMapping("modifier_utilisateur/")
    public ResponseEntity<Boolean> updateUserInfos(@RequestBody UpdateAccountUser data){
        return ResponseEntity.ok(utilisateurService.updateUserInfo(data));
    }

}