package backend.controller;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.service.contract.Affaire_DossierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/affaires/")
public class Affaire_DossierController {

    private Affaire_DossierService affaireDossierService;

    @PostMapping("creer")
    public ResponseEntity<Boolean> creerUneNouvelleAffaire(@RequestBody NewAffaire newAffaire){
        return ResponseEntity.ok(affaireDossierService.creer_affaire(newAffaire));
    }

    @GetMapping("find-by-id/{id}")
    public ResponseEntity<Affaire_DossierDto> findById(@PathVariable long id){
        return ResponseEntity.ok(affaireDossierService.findById(id));
    }

    @GetMapping("find-all")
    public ResponseEntity<List<Affaire_DossierDto>> findAll(){
        return ResponseEntity.ok(affaireDossierService.findAll());
    }

    @GetMapping("find-all-archived-affairs")
    public ResponseEntity<List<Affaire_DossierDto>> findAllArchived(){
        return ResponseEntity.ok(affaireDossierService.findAllArchivedAffairs());
    }

    @GetMapping("find-all-unarchived-affairs")
    public ResponseEntity<List<Affaire_DossierDto>> findAllUnArchived(){
        return ResponseEntity.ok(affaireDossierService.findAllUnArchivedAffairs());
    }

    @PutMapping("archive-affaire/{idAffaire}")
    public ResponseEntity<Boolean> archiveAffaire(@PathVariable long idAffaire){
        return ResponseEntity.ok(affaireDossierService.archiveAffaire(idAffaire));
    }

}