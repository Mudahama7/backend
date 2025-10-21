package backend.dto.updateEntityRequest;

import lombok.Data;

@Data
public class UpdateAffaire {

    String idAffaire;

    String nomCompletPlaignant ;
    String adressePlaignant;
    String telephonePlaignant ;
    String emailPlaignant ;
    String identifiantLegalPlaignant ;

    String nomCompletDefendeur ;
    String adresseDefendeur ;
    String telephoneDefendeur ;
    String emailDefendeur ;
    String identifiantLegalDefendeur ;

    String natureLitige ;
    String descriptionFaits;

}
