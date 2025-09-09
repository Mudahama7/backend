package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewSharingAffaireRequest {

    private String idDossier;
    private String nomDestinataire;
    private String resumeDossierPartage;

}
