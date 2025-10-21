package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewAudience {

    private String idPlainte;
    private String annee;
    private String jour;
    private String mois;
    private String dateAudience;
    private String heureDebut;
    private String causeInscrit;

}