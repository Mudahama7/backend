package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewBureau {

    private String libelle_bureau;
    private String libelle_service;

}