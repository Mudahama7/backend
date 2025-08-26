package backend.service.contract;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;

import java.util.List;

public interface Affaire_DossierService {

    boolean creer_affaire(NewAffaire  newAffaire);

    List<Affaire_DossierDto> findAll();

    Affaire_DossierDto findById(long id);

    List<Affaire_DossierDto> findAllArchivedAffairs();

    List<Affaire_DossierDto> findAllUnArchivedAffairs();

    boolean archiveAffaire(Long idAffaire);

}
