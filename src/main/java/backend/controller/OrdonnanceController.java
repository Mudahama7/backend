package backend.controller;

import backend.dto.newEntityRequest.NewOrdonnance;
import backend.exception.type_exception.SignatureMissMatchException;
import backend.service.business_logic.OrdonnanceService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/authentication/")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    @PostMapping("testOrdonnance/")
    public ResponseEntity<?> nouvelleOrdonnance(@RequestBody NewOrdonnance data) throws Exception {
        if (ordonnanceService.verifyIfSignaturesExist()) {

            byte[] pdfBytes = ordonnanceService.creerOrdonnance(data);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=nouvelle_audience.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(new ByteArrayResource(pdfBytes));
        } else {

            throw new SignatureMissMatchException("Impossible de fixer une ordonnance : signatures manquantes.");

        }
    }

}
