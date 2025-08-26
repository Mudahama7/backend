package backend.dto.newEntityRequest;

import lombok.Data;

@Data
public class NewAffaire {

    private String libelle_affaire;
    private String categorie_affaire;

}