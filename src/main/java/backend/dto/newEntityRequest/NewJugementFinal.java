package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class NewJugementFinal {

    private String idDossier;
    private String montantDommage;
    private String statutJugement;
    private MultipartFile pieceJointe;
    private String resumeDecision;

}
