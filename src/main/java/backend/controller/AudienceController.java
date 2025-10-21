package backend.controller;

import backend.dto.newEntityRequest.NewAudience;
import backend.exception.type_exception.SignatureMissMatchException;
import backend.model.Plainte;
import backend.service.business_logic.AudienceService;
import backend.service.business_logic.PlainteService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/audience/")
public class AudienceController {

    private final AudienceService audienceService;
    private final PlainteService  plainteService;

    @PreAuthorize("hasAuthority('fixer_date_pour_une_affaire')")
    @PostMapping("nouvelle_audience/")
    public ResponseEntity<?> nouvelleAudience(@RequestBody NewAudience newAudience) throws Exception {
        if (audienceService.verifyIfSignaturesExist()) {

            Plainte concernedAffaire = plainteService.findById(newAudience.getIdPlainte());

            byte[] pdfBytes = audienceService.fixerNewAudience(newAudience, concernedAffaire);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=nouvelle_audience.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(new ByteArrayResource(pdfBytes));
        } else {

            throw new SignatureMissMatchException("Impossible de fixer une audience : signatures manquantes.");

        }
    }

    @PreAuthorize("hasAuthority('signer_ordonnancement')")
    @PutMapping("signer_ordonnancement/{idAudience}")
    public ResponseEntity<Boolean> signerOrdonnancement(@PathVariable String idAudience) throws Exception {
        return ResponseEntity.ok(audienceService.signerOrdonnByPreso(idAudience));
    }

}