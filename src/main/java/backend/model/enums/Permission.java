package backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    CONFIGURATION_SYSTEM("configuration_system"),

    CREATE_AFFAIRE("create_affaire"),
    CONSULTER_AFFAIRE("consulter_affaire"),
    APPROUVER_AFFAIRE("approuver_affaire"),
    FIXER_DATE_POUR_UNE_AFFAIRE("fixer_date_pour_une_affaire"),

    CREER_ORDONNANCEMENT("creer_ordonnancement"),

    ACQUISITION_DOCUMENT("acquisition_document"),
    MODIFIER_DOCUMENT("modifier_document"),
    VALIDER_DOCUMENT("valider_document"),
    INDEXER_DOCUMENT("indexer_document"),
    CLASSER_DOCUMENT("classer_document"),
    SECURISER_DOCUMENT("securiser_document"),
    IMPRIMER_DOCUMENT("imprimer_document"),
    SUPPRIMER_DOCUMENT("supprimer_document"),
    ROUTAGE_DOCUMENT("routage_document"),
    CONSULTER_DOCUMENT("consulter_document"),

    MODIFIER_PROFIL("modifier_profil"),
    GERER_COMPTE_UTILISATEUR("gerer_compte_utilisateur");

    private final String permission;

}