package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateDepartementRequest {

    private Long  id;
    private String new_libelle_departement;

}
