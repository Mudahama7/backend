package backend.controller;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.service.business_logic.PlainteService;
import backend.service.utils.ConnectedUserGetter;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/plainte/")
public class PlainteController {

    private final PlainteService plainteService;
    private final ConnectedUserGetter connectedUserGetter;

    @PreAuthorize("hasAuthority('create_affaire')")
    @PostMapping("nouvelle_affaire/")
    public ResponseEntity<Boolean> createPlainte(@RequestBody NewAffaire newAffaire) throws MessagingException {
        System.out.println("Authorities : "+connectedUserGetter.getConnectedUser().getRole().getGrantedAuthorities());
        return ResponseEntity.ok(plainteService.createAffaire(newAffaire));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("find_all/")
    public ResponseEntity<List<AffaireDtoPourList>> findAffairesSecretaires(){
        return ResponseEntity.ok(plainteService.findAll());
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("find_all_amount/")
    public ResponseEntity<Map<String, Object>> findAffairesSecretairesAmount(){
        return ResponseEntity.ok(plainteService.findAll_Amount());
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

}