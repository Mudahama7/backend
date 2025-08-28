package backend.service.contract;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.updateEntityRequest.FixerDateAffaire;
import backend.model.Affaire_Ou_Dossier;

import java.util.List;

public interface Affaire_DossierService {

    boolean creer_affaire(NewAffaire  newAffaire);

    List<Affaire_DossierDto> findAll();

    Affaire_DossierDto findDtoObjectById(long id);

    Affaire_Ou_Dossier findEntityById(long id);

    List<Affaire_DossierDto> findAllArchivedAffairs();

    List<Affaire_DossierDto> findAllUnArchivedAffairs();

    boolean archiveAffaire(Long idAffaire);

    List<Affaire_DossierDto> findAffairsWePaidFor();

    Boolean approuverAffaire(long idAffaire);

    Boolean fixerDateDebutAffaire(FixerDateAffaire dto);
}
