package backend.service.business_logic;

import backend.dto.newEntityRequest.NewPieceJointe;
import backend.model.Audience;
import backend.model.PieceJointeAuDossier;
import backend.model.Plainte;

import java.util.List;

public interface PieceJointeService {

    boolean joinPieceToAffaire(NewPieceJointe newPieceJointe);

    List<PieceJointeAuDossier> findAllPerDossier(Plainte plainte);

}