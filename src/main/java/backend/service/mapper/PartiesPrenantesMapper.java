package backend.service.mapper;

import backend.dto.subObjects.PartiesPrenantes;
import backend.model.PartiesPrenantesAuProces;
import backend.repository.PartiesPrenantesAuProcesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PartiesPrenantesMapper {

    private final PartiesPrenantesAuProcesRepository partiesPrenantesAuProcesRepository;

    public PartiesPrenantesAuProces mapFromDtoToEntity(PartiesPrenantes dto) {
        PartiesPrenantesAuProces partiesPrenantesAuProces = new PartiesPrenantesAuProces();

        partiesPrenantesAuProces.setAdresse(dto.getAdresse());
        partiesPrenantesAuProces.setNom(dto.getNom());
        partiesPrenantesAuProces.setTelephone(dto.getTelephone());
        partiesPrenantesAuProces.setIdentifiantLegal(dto.getIdentifiantLegal());
        partiesPrenantesAuProces.setEmail(dto.getEmail());

        partiesPrenantesAuProcesRepository.save(partiesPrenantesAuProces);

        return partiesPrenantesAuProces;
    }

    public PartiesPrenantes mapFromEntityToDtoForPlainteDetails(PartiesPrenantesAuProces entity) {
        return PartiesPrenantes.builder()
                .nom(entity.getNom())
                .adresse(entity.getAdresse())
                .email(entity.getEmail())
                .identifiantLegal(entity.getIdentifiantLegal())
                .telephone(entity.getTelephone())
                .build();
    }

}
