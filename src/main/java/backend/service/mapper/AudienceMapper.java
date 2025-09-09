package backend.service.mapper;

import backend.dto.SharingAudienceInfo;
import backend.dto.newEntityRequest.NewAudience;
import backend.dto.subObjects.AudiencePourAffaireDetails;
import backend.model.Audience;
import backend.model.Plainte;
import backend.service.business_logic.PlainteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AudienceMapper {

    public Audience fromDtoToEntity(NewAudience  newAudience, Plainte plainte) {
        Audience audience = new Audience();

        audience.setIdDossier(plainte);
        audience.setAnneeAudience(newAudience.getAnnee());
        audience.setJourAudience(newAudience.getJour());
        audience.setMoisAudience(newAudience.getMois());
        audience.setDateAudience(newAudience.getDateAudience());
        audience.setHeureAudience(newAudience.getHeureDebut());
        audience.setCauseInscrit(newAudience.getCauseInscrit());

        return audience;

    }

    public SharingAudienceInfo mapToSharingAudienceInfo(NewAudience  newAudience, String emailGreffier, Plainte plainte) {

        return SharingAudienceInfo.builder()
                .nomDossier(plainte.getPlaignant().getNom() +" Vs "+ plainte.getDefendeur().getNom())
                .dateAudience(newAudience.getDateAudience())
                .heureDebut(newAudience.getHeureDebut())
                .emailGreffier(emailGreffier)
                .emailDefendeur(plainte.getDefendeur().getEmail())
                .emailPlaignant(plainte.getPlaignant().getEmail())
                .emailSecretaire(plainte.getDeposeChez().getEmail())
                .build();
    }

    public AudiencePourAffaireDetails mapFromEntityToAudiencePourAffaireDetails(Audience audience) {
        return AudiencePourAffaireDetails.builder()
                .dateAudience(audience.getDateAudience())
                .heureAudience(audience.getHeureAudience())
                .urlAudienceFile(audience.getUrlFile())
                .causeInscrite(audience.getCauseInscrit())
                .build();
    }



}
