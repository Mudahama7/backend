package backend.dto.updateEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateDepartement {

    private Long  id;
    private String new_libelle_departement;

}
