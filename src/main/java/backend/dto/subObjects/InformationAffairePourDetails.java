package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InformationAffairePourDetails {

    private String id;
    private String dateDepotPlainte;
    private String natureLitige;
    private String status;
    private String autresParties;
    private String descriptionDesFaits;

}
