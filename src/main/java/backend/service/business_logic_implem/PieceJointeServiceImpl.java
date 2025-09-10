package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewPieceJointe;
import backend.model.PieceJointeAuDossier;
import backend.model.Plainte;
import backend.model.enums.TypePieceJointe;
import backend.repository.PieceJointeAuDossierRepository;
import backend.service.business_logic.PieceJointeService;
import backend.service.business_logic.PlainteService;
import backend.service.utils.ConnectedUserGetter;
import backend.service.utils.SupabaseStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;


@AllArgsConstructor
@Service
public class PieceJointeServiceImpl implements PieceJointeService {

    private final PieceJointeAuDossierRepository pieceJointeAuDossierRepository;
    private final PlainteService plainteService;
    private final SupabaseStorageService supabaseStorageService;
    private final ConnectedUserGetter connectedUserGetter;

    @Override
    public boolean joinPieceToAffaire(NewPieceJointe newPieceJointe) throws IOException {
        Plainte concernedAffair = plainteService.findById(newPieceJointe.getIdAffaire());
        String ulrFileSaved = supabaseStorageService.uploadFile(
                newPieceJointe.getMultipartFile().getBytes(),
                newPieceJointe.getNomFile(),
                "application/pdf"
        );

        PieceJointeAuDossier pieceJointeAuDossier = new PieceJointeAuDossier();

        pieceJointeAuDossier.setTypePieceJointe(TypePieceJointe.valueOf(newPieceJointe.getTypePiece()));
        pieceJointeAuDossier.setPlainte(concernedAffair);
        pieceJointeAuDossier.setAjouteePar(connectedUserGetter.getConnectedUser());
        pieceJointeAuDossier.setNomFichier(newPieceJointe.getNomFile());
        pieceJointeAuDossier.setUrlFichier(ulrFileSaved);

        pieceJointeAuDossierRepository.save(pieceJointeAuDossier);

        return true;
    }


}
