package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewPieceJointe;
import backend.repository.PieceJointeAuDossierRepository;
import backend.service.business_logic.PieceJointeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PieceJointeServiceImpl implements PieceJointeService {

    private final PieceJointeAuDossierRepository pieceJointeAuDossierRepository;

    @Override
    public boolean joinPieceToAffaire(NewPieceJointe newPieceJointe) {
        return false;
    }
}
