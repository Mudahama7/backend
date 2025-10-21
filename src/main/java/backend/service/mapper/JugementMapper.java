package backend.service.mapper;

import backend.dto.newEntityRequest.NewJugementFinal;
import backend.dto.subObjects.JugementsInformations;
import backend.model.JugementFinal;
import backend.model.Plainte;
import backend.model.enums.StatutJugement;
import backend.service.utils.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JugementMapper {

    private final MinioService minioService;

    public JugementFinal mapFromNewJugementFinalReqToJugementFinal(NewJugementFinal newJugementFinal, Plainte plainte) throws Exception {

        JugementFinal jugementFinal = new JugementFinal();
        jugementFinal.setIdDossier(plainte);
        jugementFinal.setResumeDecision(newJugementFinal.getResumeDecision());
        jugementFinal.setMontantDomages(Double.valueOf(newJugementFinal.getMontantDommage()));
        jugementFinal.setStatut(StatutJugement.valueOf(newJugementFinal.getStatutJugement()));

        String urlFile = minioService.uploadFile(newJugementFinal.getPieceJointe());

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