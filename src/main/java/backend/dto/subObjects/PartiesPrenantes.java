package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartiesPrenantes {

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String identifiantLegal;

}
