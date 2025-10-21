package backend.dto;

import backend.dto.subObjects.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AffaireDetails {

    private PartiesPrenantes infoPlaignant;
    private PartiesPrenantes infoDefendeur;
    private InformationAffairePourDetails informationDeLAffaire;
    private List<HistoriquePartageDossierDto> historiquePartageDossierDtos;
    private List<AudiencePourAffaireDetails> AudiencePourAffaireDetails;
    private List<PieceJointePourAffaireDetails> PieceJointePourAffaireDetails;
    private List<JugementsInformations> JugementsInformations;

}