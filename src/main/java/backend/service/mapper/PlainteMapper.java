package backend.service.mapper;

import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Plainte;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlainteMapper {

    private final PartiesPrenantesMapper partiesPrenantesMapper;

    public Plainte mapFromNewPlainteRequestToEntity(NewAffaire newAffaire) {
        Plainte plainte = new Plainte();

        plainte.setPlaignant(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getPlaignant()));
        plainte.setDefendeur(partiesPrenantesMapper.mapFromDtoToEntity(newAffaire.getDefendeur()));
        plainte.setNatureLitige(newAffaire.getNatureLitige());
        plainte.setCaution(Double.valueOf(newAffaire.getCaution()));
        plainte.setDescriptionDesFaits(newAffaire.getDescriptionDuFaits());
        plainte.setAutresParties(newAffaire.getAutresParties());

        return plainte;

    }

    public AffaireDtoPourList mapFromEntityToAffaireDtoList(Plainte plainte) {
        return AffaireDtoPourList.builder()
                .libelePlainte(plainte.getPlaignant().getNom() +" Vs "+ plainte.getDefendeur().getNom())
                .statutPlainte(plainte.getStatutDossier().toString())
                .build();
    }

}