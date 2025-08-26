package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Affaire_DossierDto {

    private Long id;
    private String libelleAffaire;
    private String categorieAffaire;

}