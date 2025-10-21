package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AudiencePourAffaireDetails {

    private String dateAudience;
    private String heureAudience;
    private String urlAudienceFile;
    private String nameFile;
    private String causeInscrite;
    private boolean signedByThePresident;

}
