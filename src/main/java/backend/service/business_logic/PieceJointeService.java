package backend.service.business_logic;

import backend.dto.newEntityRequest.NewPieceJointe;

public interface PieceJointeService {

    boolean joinPieceToAffaire(NewPieceJointe newPieceJointe);

}