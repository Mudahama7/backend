package backend.service.mapper;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.subObjects.*;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlainteMapper {

    private final PartiesPrenantesMapper partiesPrenantesMapper;
    private final PartageDossierMapper partageDossierMapper;
    private final AudienceMapper audienceMapper;
    private final PieceJointeMapper pieceJointeMapper;
    private final JugementMapper jugementMapper;

    public Plainte mapFromNewPlainteRequestToEntity(NewAffaire newAffaire) {
        Plainte plainte = new Plainte();

        plainte.setPlaignant(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getPlaignant()));
        plainte.setDefendeur(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getDefendeur()));
        plainte.setNatureLitige(newAffaire.getNatureLitige());
        plainte.setDescriptionDesFaits(newAffaire.getDescriptionDuFaits());
        plainte.setCaution(Double.valueOf(newAffaire.getCaution()));
        plainte.setAutresParties(newAffaire.getAutresParties());

        return plainte;

    }

    public AffaireDtoPourList mapFromEntityToAffaireDtoList(Plainte plainte) {
        return AffaireDtoPourList.builder()
                .idPlainte(String.valueOf(plainte.getIdDossier()))
                .libelePlainte(plainte.getPlaignant().getNom() +" Vs "+ plainte.getDefendeur().getNom())
                .statutPlainte(plainte.getStatutDossier().toString())
                .build();
    }

    public AffaireDtoPourList mapFromHistoriqueDePartage(HistoriquePartageDuDossier historiquePartageDuDossier){
        return mapFromEntityToAffaireDtoList(historiquePartageDuDossier.getAffaireShared());
    }

    public AffaireDetails mapFromPlainteEntityToPlainteDetails(Plainte plainte){
        return AffaireDetails.builder()
                .infoPlaignant(partiesPrenantesMapper.mapFromEntityToDtoForPlainteDetails(plainte.getPlaignant()))
                .infoDefendeur(partiesPrenantesMapper.mapFromEntityToDtoForPlainteDetails(plainte.getDefendeur()))
                .informationDeLAffaire(
                        InformationAffairePourDetails.builder()
                                .id(String.valueOf(plainte.getIdDossier()))
                                .dateDepotPlainte(String.valueOf(plainte.getDateDepotPlainte()))
                                .autresParties(plainte.getAutresParties())
                                .descriptionDesFaits(plainte.getDescriptionDesFaits())
                                .natureLitige(plainte.getNatureLitige())
                                .status(plainte.getStatutDossier().toString())
                                .build())
                .historiquePartageDossierDtos(plainte.getHistoriquePartageDuDossiers().stream().map(partageDossierMapper::mapFromEntityToDto).toList())
                .listDAudiences(plainte.getListeDAudiences().stream().map(audienceMapper::mapFromEntityToAudiencePourAffaireDetails).toList())
                .listPiecesJointes(plainte.getPieceJointeAuDossiers().stream().map(pieceJointeMapper::mapFromEntityToDto).toList())
                .listJugements(plainte.getJugementFinal().stream().map(jugementMapper::mapFromEntityToPlainteInformations).toList())
                .build();
    }

}

