package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PieceJointePourAffaireDetails {

    private String dateAjout;
    private String nomFile;
    private String typeFile;
    private String responsableAjout;
    private String urlFile;

}
