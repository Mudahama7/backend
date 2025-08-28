package backend.service.implentation;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.updateEntityRequest.FixerDateAffaire;
import backend.model.Affaire_Ou_Dossier;
import backend.repository.Affaire_DossierRepository;
import backend.service.contract.Affaire_DossierService;
import backend.service.mapper.AffaireMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class Affaire_DossierServiceImpl implements Affaire_DossierService {

    private final Affaire_DossierRepository  affaire_DossierRepository;
    private final AffaireMapper affaireMapper;

    @Override
    public boolean creer_affaire(NewAffaire newAffaire) {
        Affaire_Ou_Dossier affaireOuDossier = affaireMapper.fromRequestToObject(newAffaire);
        affaire_DossierRepository.save(affaireOuDossier);
        return true;
    }

    @Override
    public List<Affaire_DossierDto> findAll() {
        return affaire_DossierRepository.findAll().stream().map(affaireMapper::fromEntityToDto).toList();
    }

    @Override
    public Affaire_DossierDto findDtoObjectById(long id) {
        return affaireMapper.fromEntityToDto(affaire_DossierRepository.findById(id).get());
    }

    @Override
    public Affaire_Ou_Dossier findEntityById(long id) {
        return affaire_DossierRepository.findById(id).orElse(null);
    }

    @Override
    public List<Affaire_DossierDto> findAllArchivedAffairs() {
        return affaire_DossierRepository.findAllByArchived(true).stream().map(affaireMapper::fromEntityToDto).toList();
    }

    @Override
    public List<Affaire_DossierDto> findAllUnArchivedAffairs() {
        return affaire_DossierRepository.findAllByArchived(false).stream().map(affaireMapper::fromEntityToDto).toList();
    }

    @Override
    public boolean archiveAffaire(Long idAffaire) {
        Affaire_Ou_Dossier affaireOuDossier = affaire_DossierRepository.findById(idAffaire).get();
        affaireOuDossier.setArchived(true);
        return true;
    }

    @Override
    public List<Affaire_DossierDto> findAffairsWePaidFor() {
        return affaire_DossierRepository.findAffairsWePaidFor().stream().map(affaireMapper::fromEntityToDto).toList();
    }

    @Override
    public Boolean approuverAffaire(long idAffaire) {
        Affaire_Ou_Dossier affaireOuDossier = findEntityById(idAffaire);
        affaireOuDossier.setApprobation_affair(true);
        affaire_DossierRepository.save(affaireOuDossier);
        return true;
    }

    @Override
    public Boolean fixerDateDebutAffaire(FixerDateAffaire dto) {
        Affaire_Ou_Dossier affaireOuDossier = findEntityById(Long.parseLong(dto.getIdAffaire()));
        affaireOuDossier.setDate_debut(LocalDate.parse(dto.getDateDebut()));
        return null;
    }
}