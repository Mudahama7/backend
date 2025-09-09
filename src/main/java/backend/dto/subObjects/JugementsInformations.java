package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JugementsInformations {

    private String dateJugement;
    private String montantDommage;
    private String statusJugement;
    private String urlFile;
    private String resumeDecision;

}
