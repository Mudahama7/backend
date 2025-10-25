package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewOrdonnance {

    String idAffaire;
    String portantSur;
    String corsDocument;

}
