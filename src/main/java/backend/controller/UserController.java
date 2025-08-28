package backend.controller;

import backend.dto.newEntityRequest.NewUser;
import backend.service.contract.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/users/")
public class UserController {

    private final UserService userService;

    @PostMapping("ajouter-utilisateur")
    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    public ResponseEntity<Boolean> ajouter_utilisateur(@RequestBody NewUser user) throws MessagingException {
        return ResponseEntity.ok(userService.ajouter_user(user));
    }

    @DeleteMapping("supprimer-utilisateur/{idUser}")
    @PreAuthorize("hasAuthority('gerer_compte_utilisateur')")
    public ResponseEntity<Boolean> supprimer_utilisateur(@PathVariable("idUser") String idUser){
        return ResponseEntity.ok(userService.supprimer_user(idUser));
    }

}