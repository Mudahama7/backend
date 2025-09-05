package backend.service.business_logic;

import backend.dto.newEntityRequest.NewSharingAffaireRequest;

public interface PartageAffaireService {

    boolean shareAffaire(NewSharingAffaireRequest newSharingAffaireRequest);

    boolean generateNoteWhenSharingAffaire();

}