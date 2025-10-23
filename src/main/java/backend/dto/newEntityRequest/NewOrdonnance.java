package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewOrdonnance {

    String idPlainte;
    String numeroOrdonnance;
    String portantSur;
    String corpsDocument;

}
