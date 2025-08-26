package backend.service.implentation;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Affaire_Ou_Dossier;
import backend.repository.Affaire_DossierRepository;
import backend.service.contract.Affaire_DossierService;
import backend.service.mapper.AffaireMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Affaire_DossierDto findById(long id) {
        return affaireMapper.fromEntityToDto(affaire_DossierRepository.findById(id).get());
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
}