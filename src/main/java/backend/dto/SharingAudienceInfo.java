package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SharingAudienceInfo {

    private String nomDossier;
    private String dateAudience;
    private String heureDebut;

    private String emailGreffier;
    private String emailSecretaire;
    private String emailPlaignant;
    private String emailDefendeur;

}