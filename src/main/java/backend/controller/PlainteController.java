package backend.controller;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.updateEntityRequest.UpdateAffaire;
import backend.service.business_logic.PlainteService;
import backend.service.utils.ConnectedUserGetter;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/plainte/")
public class PlainteController {

    private final PlainteService plainteService;
    private final ConnectedUserGetter connectedUserGetter;

    @PreAuthorize("hasAuthority('create_affaire')")
    @PostMapping("nouvelle_affaire/")
    public ResponseEntity<Boolean> createPlainte(@RequestBody NewAffaire newAffaire) throws MessagingException {
        return ResponseEntity.ok(plainteService.createAffaire(newAffaire));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("find_all/")
    public ResponseEntity<List<AffaireDtoPourList>> findAffairesSecretaires(){
        return ResponseEntity.ok(plainteService.findAll());
    }

    @PreAuthorize("hasAuthority('approuver_affaire')")
    @PutMapping("validation_greffier/{idDossier}/")
    public ResponseEntity<Boolean> validationGreffier(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.approbationAffaireGreffier(idDossier));
    }


    @PreAuthorize("hasAuthority('approuver_affaire')")
    @PutMapping("validation_president/{idDossier}/")
    public ResponseEntity<Boolean> validationPresident(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.approbationAffairePresident(idDossier));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("voir_detail_affaire/{idDossier}/")
    public ResponseEntity<AffaireDetails> voirDetailsAffaire(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.findAllAffairDetails(idDossier));
    }

    @PreAuthorize("hasAuthority('modifier_affaire')")
    @PutMapping("modifier_affaire/")
    public ResponseEntity<Boolean> modifierAffaire(@RequestBody UpdateAffaire updateAffaire){
        return ResponseEntity.ok(plainteService.updateAffaireInfo(updateAffaire));
    }

}