package backend.controller;

import backend.dto.newEntityRequest.NewJugementFinal;
import backend.model.Plainte;
import backend.service.business_logic.JugementService;
import backend.service.business_logic.PlainteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/jugement/")
public class JugementController {

    private final JugementService jugementService;
    private final PlainteService plainteService;

    @PreAuthorize("hasAuthority('prononcer_son_jugement')")
    @PostMapping("en_prononcer_un/")
    public ResponseEntity<?> prononcerSonJugement(
            @RequestParam("idDossier") String idDossier,
            @RequestParam("montantDommage") String montantDommage,
            @RequestParam("statutJugement") String statutJugement,
            @RequestParam("pieceJointe") MultipartFile pieceJointe,
            @RequestParam("resumeDecision") String resumeDecision
    ) throws IOException {
        NewJugementFinal jugementFinal = NewJugementFinal.builder()
                .idDossier(idDossier)
                .montantDommage(montantDommage)
                .statutJugement(statutJugement)
                .pieceJointe(pieceJointe)
                .resumeDecision(resumeDecision)
                .build();

        Plainte concernedAffair = plainteService.findById(idDossier);

        return ResponseEntity.ok(jugementService.prononcerUnJugement(jugementFinal, concernedAffair));

    }


}
