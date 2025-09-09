package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewPieceJointe;
import backend.model.PieceJointeAuDossier;
import backend.model.Plainte;
import backend.repository.PieceJointeAuDossierRepository;
import backend.service.business_logic.PieceJointeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PieceJointeServiceImpl implements PieceJointeService {

    private final PieceJointeAuDossierRepository pieceJointeAuDossierRepository;

    @Override
    public boolean joinPieceToAffaire(NewPieceJointe newPieceJointe) {
        return false;
    }


    @Override
    public List<PieceJointeAuDossier> findAllPerDossier(Plainte plainte) {
        return pieceJointeAuDossierRepository.findAllByPlainte(plainte);
    }
}
