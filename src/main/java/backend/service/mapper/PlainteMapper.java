package backend.service.mapper;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.subObjects.*;
import backend.model.HistoriquePartageDuDossier;
import backend.model.PartiesPrenantesAuProces;
import backend.model.Plainte;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import backend.dto.updateEntityRequest.UpdateAffaire;

@AllArgsConstructor
@Service
public class PlainteMapper {

    private final PartiesPrenantesMapper partiesPrenantesMapper;
    private final PartageDossierMapper partageDossierMapper;
    private final AudienceMapper audienceMapper;
    private final PieceJointeMapper pieceJointeMapper;
    private final JugementMapper jugementMapper;
    private final OrdonnanceMapper ordonnanceMapper;

    public Plainte mapFromNewPlainteRequestToEntity(NewAffaire newAffaire) {
        Plainte plainte = new Plainte();

        plainte.setPlaignant(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getPlaignant()));
        plainte.setDefendeur(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getDefendeur()));
        plainte.setNatureLitige(newAffaire.getNatureLitige());
        plainte.setDescriptionDesFaits(newAffaire.getDescriptionDesFaits());
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
                .ordonnancePourAffairDetails(plainte.getOrdonnances().stream().map(ordonnanceMapper::mapFromEntityToOrdonnanceAffairDetails).toList())
                .AudiencePourAffaireDetails(plainte.getListeDAudiences().stream().map(audienceMapper::mapFromEntityToAudiencePourAffaireDetails).toList())
                .PieceJointePourAffaireDetails(plainte.getPieceJointeAuDossiers().stream().map(pieceJointeMapper::mapFromEntityToDto).toList())
                .JugementsInformations(plainte.getJugementFinal().stream().map(jugementMapper::mapFromEntityToPlainteInformations).toList())
                .build();
    }

    public Plainte updateInformations(UpdateAffaire updateAffaire, Plainte affaireToUpdate){

        PartiesPrenantesAuProces infoPlaingant = affaireToUpdate.getPlaignant();
        infoPlaingant.setNom(updateAffaire.getNomCompletPlaignant());
        infoPlaingant.setAdresse(updateAffaire.getAdressePlaignant());
        infoPlaingant.setTelephone(updateAffaire.getTelephonePlaignant());
        infoPlaingant.setEmail(updateAffaire.getEmailPlaignant());
        infoPlaingant.setIdentifiantLegal(updateAffaire.getIdentifiantLegalPlaignant());

        PartiesPrenantesAuProces infoDefendeur = affaireToUpdate.getDefendeur();
        infoDefendeur.setNom(updateAffaire.getNomCompletDefendeur());
        infoDefendeur.setAdresse(updateAffaire.getAdresseDefendeur());
        infoDefendeur.setTelephone(updateAffaire.getTelephoneDefendeur());
        infoDefendeur.setEmail(updateAffaire.getEmailDefendeur());
        infoDefendeur.setIdentifiantLegal(updateAffaire.getIdentifiantLegalDefendeur());

        affaireToUpdate.setNatureLitige(updateAffaire.getNatureLitige());
        if (!updateAffaire.getDescriptionFaits().isEmpty()){
            affaireToUpdate.setDescriptionDesFaits(updateAffaire.getDescriptionFaits());
        }
        affaireToUpdate.setPlaignant(infoPlaingant);
        affaireToUpdate.setDefendeur(infoDefendeur);

        return affaireToUpdate;
    }

}

