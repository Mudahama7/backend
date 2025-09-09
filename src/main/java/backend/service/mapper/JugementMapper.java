package backend.service.mapper;

import backend.dto.newEntityRequest.NewJugementFinal;
import backend.dto.subObjects.JugementsInformations;
import backend.model.JugementFinal;
import backend.model.Plainte;
import backend.model.enums.StatutJugement;
import backend.service.utils.SupabaseStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JugementMapper {

    private final SupabaseStorageService supabaseStorageService;

    public JugementFinal mapFromNewJugementFinalReqToJugementFinal(NewJugementFinal newJugementFinal, Plainte plainte) throws IOException {

        JugementFinal jugementFinal = new JugementFinal();
        jugementFinal.setIdDossier(plainte);
        jugementFinal.setResumeDecision(newJugementFinal.getResumeDecision());
        jugementFinal.setMontantDomages(Double.valueOf(newJugementFinal.getMontantDommage()));
        jugementFinal.setStatut(StatutJugement.valueOf(newJugementFinal.getStatutJugement()));

        byte[] copieJugementScannee = newJugementFinal.getPieceJointe().getBytes();
        String fileName = "jugement final dans l'affaire "+ plainte.getPlaignant().getNom() +" Vs "+ plainte.getDefendeur().getNom();
        String urlFile = supabaseStorageService.uploadFile(copieJugementScannee, fileName, "application/pdf");

        jugementFinal.setCopieJugementScannee(urlFile);

        return  jugementFinal;

    }

    public JugementsInformations mapFromEntityToPlainteInformations(JugementFinal jugementFinal) {
        return JugementsInformations.builder()
                .dateJugement(jugementFinal.getDateJugement().toString())
                .montantDommage(jugementFinal.getMontantDomages().toString())
                .statusJugement(jugementFinal.getStatut().name())
                .resumeDecision(jugementFinal.getResumeDecision())
                .urlFile(jugementFinal.getCopieJugementScannee())
                .build();
    }


}