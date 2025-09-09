package backend.dto.newEntityRequest;

import lombok.Data;

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