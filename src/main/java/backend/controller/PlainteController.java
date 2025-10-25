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
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/plainte/")
public class PlainteController {

    private final PlainteService plainteService;

    @PreAuthorize("hasAuthority('create_affaire')")
    @PostMapping("nouvelle_affaire/")
    public ResponseEntity<Boolean> createPlainte(@RequestBody NewAffaire newAffaire) throws MessagingException {
        return ResponseEntity.ok(plainteService.createAffaire(newAffaire));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("find_all/")
    public ResponseEntity<List<AffaireDtoPourList>> findAllAffairs(){
        return ResponseEntity.ok(plainteService.findAll());
    }


    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("find_all_archived_affairs/")
    public ResponseEntity<List<AffaireDtoPourList>> find_all_archived_affairs(){
        return ResponseEntity.ok(plainteService.findAllArchivedAffairs());
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

    @PreAuthorize("hasAuthority('archiver_une_affaire')")
    @PutMapping("archiver/{idDossier}/")
    public ResponseEntity<Boolean> archiverAffaire(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.archiverAffaire(idDossier));
    }

    @PreAuthorize("hasAuthority('archiver_une_affaire')")
    @PutMapping("desarchiver/{idDossier}/")
    public ResponseEntity<Boolean> desarchiverAffaire(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.desarchiverAffaire(idDossier));
    }

    @PreAuthorize("hasAuthority('supprimer_une_affaire')")
    @DeleteMapping("supprimer/{idDossier}/")
    public ResponseEntity<Boolean> supprimerAffaire(@PathVariable String idDossier){
        return ResponseEntity.ok(plainteService.supprimerAffaire(idDossier));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @PostMapping("filtrer/")
    public ResponseEntity<List<AffaireDtoPourList>> filterAffairs(@RequestBody Map<String, String> filtersParam){

        String jour = filtersParam.get("jour");
        String mois = filtersParam.get("mois");
        String annee = filtersParam.get("annee");
        String ordre = filtersParam.get("ordre");

        return ResponseEntity.ok(plainteService.filterAffairs(jour, mois, annee, ordre));
    }


}