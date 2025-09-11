package backend.service.business_logic;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.subObjects.HistoriquePartageDossierDto;
import backend.model.Plainte;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;

public interface PlainteService {

    boolean createAffaire(NewAffaire newAffaire) throws MessagingException;

    Plainte findById(String id);

    List<AffaireDtoPourList> findAll();

    List<AffaireDtoPourList> findDossiersQuiMeSontPartages();

    List<AffaireDtoPourList> filterAllAffairsPerStatus(String status);

    AffaireDetails findAllAffairDetails(String id);

    Boolean approbationAffaireGreffier(String idAffaire);

    Boolean approbationAffairePresident(String idAffaire);

    Map<String, Object> findAll_Amount();

}