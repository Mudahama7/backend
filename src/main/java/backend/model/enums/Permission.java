package backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    CREATE_AFFAIRE("create_affaire"),
    ACQUISITION_DOCUMENT("acquisition_document"),
    MODIFIER_DOCUMENT("modifier_document"),
    VALIDER_DOCUMENT("valider_document"),
    INDEXER_DOCUMENT("indexer_document"),
    CLASSER_DOCUMENT("classer_document"),
    SECURISER_DOCUMENT("securiser_document"),
    IMPRIMER_DOCUMENT("imprimer_document"),
    SUPPRIMER_DOCUMENT("supprimer_document"),
    ROUTAGE_DOCUMENT("routage_document"),
    MODIFIER_PROFIL("modifier_profil"),
    GERER_COMPTE_UTILISATEUR("gerer_compte_utilisateur"),
    CONSULTER_DOCUMENT("consulter_document");

    private final String permission;

}