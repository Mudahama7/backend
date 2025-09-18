package backend.controller;

import backend.dto.UserDtoPourList;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.exception.type_exception.AddInvalidUserException;
import backend.exception.type_exception.UserNotFoundException;
import backend.service.business_logic.UtilisateurService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/utilisateur/")
public class UserController {

    private final UtilisateurService utilisateurService;

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


    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    @GetMapping("get_all/")
    public ResponseEntity<List<UserDtoPourList>> findAll() {
        List<UserDtoPourList> userList = utilisateurService.getAll();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("Aucun utilisateur trouvé, à part l'admin personne d'autre n'a encore un compte !");
        }
        return ResponseEntity.ok(userList);
    }


}