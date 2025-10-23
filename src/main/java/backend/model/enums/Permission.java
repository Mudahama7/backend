package backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    CONFIGURATION_SYSTEM("configuration_system"),

    CREATE_AFFAIRE("create_affaire"),
    CONSULTER_AFFAIRE("consulter_affaire"),
    MODIFIER_AFFAIRE("modifier_affaire"),
    APPROUVER_AFFAIRE("approuver_affaire"),
    FIXER_DATE_POUR_UNE_AFFAIRE("fixer_date_pour_une_affaire"),
    PRONONCER_SON_JUGEMENT("prononcer_son_jugement"),
    PARTAGER_UNE_AFFAIRE("partager_une_affaire"),
    ARCHIVER_UNE_AFFAIRE("archiver_une_affaire"),
    SUPPRIMER_UNE_AFFAIRE("supprimer_une_affaire"),

    CREER_ORDONNANCEMENT("creer_ordonnancement"),
    SIGNER_ORDONNANCEMENT("signer_ordonnancement"),

    CREER_ORDONNANCE("creer_ordonnance"),
    SIGNER_ORDONNANCE("signer_ordonnance"),

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
    CONSULTER_UTILISATEURS("consulter_utilisateur"),
    GERER_COMPTE_UTILISATEUR("gerer_compte_utilisateur");

    private final String permission;

}