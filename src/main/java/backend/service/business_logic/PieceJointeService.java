package backend.service.business_logic;

import backend.dto.newEntityRequest.NewPieceJointe;

import java.io.IOException;

public interface PieceJointeService {

    boolean joinPieceToAffaire(NewPieceJointe newPieceJointe) throws Exception;

}