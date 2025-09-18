package backend.controller;


import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.exception.type_exception.SignatureMissMatchException;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.service.business_logic.PartageAffaireService;
import backend.service.business_logic.PlainteService;
import backend.service.utils.ConnectedUserGetter;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/partage_dossier/")
public class PartageDossierController {

    private final PartageAffaireService partageAffaireService;
    private final ConnectedUserGetter connectedUserGetter;
    private final PlainteService plainteService;

    @PreAuthorize("hasAuthority('partager_une_affaire')")
    @PostMapping("en_partager_un/")
    public ResponseEntity<?> partagerUnDossier(@RequestBody NewSharingAffaireRequest  newSharingAffaireRequest) throws IOException, MessagingException {

        Utilisateur user = connectedUserGetter.getConnectedUser();
        Plainte concernedAffair = plainteService.findById(newSharingAffaireRequest.getIdDossier());

        if (user.getSignatureUrlImage() != null) {
            return ResponseEntity.ok(partageAffaireService.shareAffaire(newSharingAffaireRequest, user, concernedAffair));
        } else {
            throw new SignatureMissMatchException("Impossible de partager le dossier : On a besoin de votre signature pour signer la note de partage ! et vous ne l'avez pas encore chargée...");
        }

    }

}
