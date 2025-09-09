package backend.service.mapper;

import backend.dto.subObjects.PartiesPrenantes;
import backend.model.PartiesPrenantesAuProces;
import org.springframework.stereotype.Service;

@Service
public class PartiesPrenantesMapper {

    public PartiesPrenantesAuProces mapFromDtoToEntity(PartiesPrenantes partiesPrenantesAuProcess) {
        PartiesPrenantesAuProces partiesPrenantesAuProces = new PartiesPrenantesAuProces();

        partiesPrenantesAuProces.setAdresse(partiesPrenantesAuProces.getAdresse());
        partiesPrenantesAuProces.setNom(partiesPrenantesAuProces.getNom());
        partiesPrenantesAuProces.setTelephone(partiesPrenantesAuProces.getTelephone());
        partiesPrenantesAuProces.setIdentifiantLegal(partiesPrenantesAuProces.getIdentifiantLegal());
        partiesPrenantesAuProces.setEmail(partiesPrenantesAuProces.getEmail());

        return partiesPrenantesAuProces;
    }

    public PartiesPrenantes mapFromEntityToDtoForPlainteDetails(PartiesPrenantesAuProces partiesPrenantesAuProcess) {
        return PartiesPrenantes.builder()
                .nom(partiesPrenantesAuProcess.getNom())
                .adresse(partiesPrenantesAuProcess.getAdresse())
                .email(partiesPrenantesAuProcess.getEmail())
                .identifiantLegal(partiesPrenantesAuProcess.getIdentifiantLegal())
                .telephone(partiesPrenantesAuProcess.getTelephone())
                .build();
    }

}
