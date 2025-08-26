package backend.dto.updateEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateBureau {

    private Long  id;
    private String new_libelle_bureau;

}