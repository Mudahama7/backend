package backend.dto.newEntityRequest;

import backend.dto.subObjects.PartiesPrenantes;
import lombok.Data;

@Data
public class NewAffaire {

    private PartiesPrenantes plaignant;
    private PartiesPrenantes defendeur;
    private String natureLitige;
    private String descriptionDuFaits;
    private String caution;
    private String autresParties;

}