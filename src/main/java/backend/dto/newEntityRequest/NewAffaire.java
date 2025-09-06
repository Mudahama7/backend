package backend.dto.newEntityRequest;

import backend.dto.newEntityRequest.subObjects.PartiesPrenantes;
import lombok.Data;

@Data
public class NewAffaire {

    private PartiesPrenantes plaignant;
    private PartiesPrenantes defendeur;
    private String natureLitige;
    private String autresParties;
    private String caution;
    private String descriptionDuFaits;

}