package backend.controller;

import backend.dto.newEntityRequest.NewOrdonnance;
import backend.exception.type_exception.SignatureMissMatchException;
import backend.service.business_logic.OrdonnanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/ordonnance/")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    @PreAuthorize("hasAuthority('creer_ordonnance')")
    @PostMapping("create_ordonnance/")
    public ResponseEntity<Boolean> nouvelleOrdonnance(@RequestBody NewOrdonnance data) throws Exception {
        if (ordonnanceService.verifyIfSignaturesExist()) {

            return ResponseEntity.ok(ordonnanceService.creerOrdonnance(data));

        } else {

            throw new SignatureMissMatchException("Impossible de fixer une ordonnance : signatures manquantes.");

        }
    }


    @PreAuthorize("hasAuthority('creer_ordonnance')")
    @PutMapping("signer_ordonnance/{idOrdonnance}")
    public ResponseEntity<Boolean> signerOrdonnance(@PathVariable String idOrdonnance) throws Exception {
        return ResponseEntity.ok(ordonnanceService.signerOrdonnance(idOrdonnance));
    }
}
