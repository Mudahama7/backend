package backend.controller;

import backend.dto.UserDtoPourList;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.service.business_logic.UtilisateurService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cette adresse mail est déjà attribuée à un autre compte ! ");
        }
        else {
            return ResponseEntity.ok(utilisateurService.ajouter_user(newUtilisateur));
        }
    }


    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    @DeleteMapping("supprimer_utilisateur/{idUser}/")
    public ResponseEntity<Boolean> supprimerUtilisateur(@PathVariable("idUser") String idUser) {
        return ResponseEntity.ok(utilisateurService.supprimer_user(idUser));
    }


    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    @GetMapping("get_all/")
    public ResponseEntity<List<UserDtoPourList>> findAll() {
        return ResponseEntity.ok(utilisateurService.getAll());
    }


}