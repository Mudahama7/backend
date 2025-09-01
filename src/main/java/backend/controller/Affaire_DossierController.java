package backend.controller;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.updateEntityRequest.FixerDateAffaire;
import backend.service.business_logic.Affaire_DossierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/affaires/")
public class Affaire_DossierController {

    private Affaire_DossierService affaireDossierService;

    @PostMapping("creer")
    @PreAuthorize("hasAuthority('create_affaire')")
    public ResponseEntity<Boolean> creerUneNouvelleAffaire(@RequestBody NewAffaire newAffaire){
        return ResponseEntity.ok(affaireDossierService.creer_affaire(newAffaire));
    }

    @GetMapping("find-by-id/{id}")
    @PreAuthorize("hasAuthority('consulter_document')")
    public ResponseEntity<Affaire_DossierDto> findById(@PathVariable long id){
        return ResponseEntity.ok(affaireDossierService.findDtoObjectById(id));
    }

    @GetMapping("find-all")
    @PreAuthorize("hasAuthority('consulter_document')")
    public ResponseEntity<List<Affaire_DossierDto>> findAll(){
        return ResponseEntity.ok(affaireDossierService.findAll());
    }

    @GetMapping("find-all-archived-affairs")
    @PreAuthorize("hasAuthority('consulter_document')")
    public ResponseEntity<List<Affaire_DossierDto>> findAllArchived(){
        return ResponseEntity.ok(affaireDossierService.findAllArchivedAffairs());
    }

    @GetMapping("find-all-unarchived-affairs")
    @PreAuthorize("hasAuthority('consulter_document')")
    public ResponseEntity<List<Affaire_DossierDto>> findAllUnArchived(){
        return ResponseEntity.ok(affaireDossierService.findAllUnArchivedAffairs());
    }

    @PutMapping("archive-affaire/{idAffaire}")
    @PreAuthorize("hasAuthority('classer_document')")
    public ResponseEntity<Boolean> archiveAffaire(@PathVariable long idAffaire){
        return ResponseEntity.ok(affaireDossierService.archiveAffaire(idAffaire));
    }

    @GetMapping("find-affairs-that-we-paid-for")
    @PreAuthorize("hasAuthority('consulter_affaire')")
    public ResponseEntity<List<Affaire_DossierDto>>  findAffaireDossierThatWePaidFor(){
        return ResponseEntity.ok(affaireDossierService.findAffairsWePaidFor());
    }

    @PutMapping("approuver-affaire/{idAffaire}")
    @PreAuthorize("hasAuthority('approuver_affaire')")
    public ResponseEntity<Boolean> approuverAffaire(@PathVariable long idAffaire){
        return ResponseEntity.ok(affaireDossierService.approuverAffaire(idAffaire));
    }

    @PutMapping("fixer-date-debut-affaire")
    @PreAuthorize("hasAuthority('fixer_date_pour_une_affaire')")
    public ResponseEntity<Boolean> fixerDateDebutAffaire(@RequestBody FixerDateAffaire fixerDateAffaire){
        return ResponseEntity.ok(affaireDossierService.fixerDateDebutAffaire(fixerDateAffaire));
    }

}