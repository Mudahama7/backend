package backend.service.mapper;

import backend.dto.subObjects.PieceJointePourAffaireDetails;
import backend.model.PieceJointeAuDossier;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class PieceJointeMapper {


    public PieceJointePourAffaireDetails mapFromEntityToDto(PieceJointeAuDossier piece){
        return PieceJointePourAffaireDetails.builder()
                .dateAjout(piece.getDateAjout().toString())
                .nomFile(piece.getNomFichier())
                .typeFile(piece.getTypePieceJointe().toString())
                .urlFile(piece.getUrlFichier())
                .responsableAjout(piece.getAjouteePar().getNomComplet())
                .build();
    }


}
