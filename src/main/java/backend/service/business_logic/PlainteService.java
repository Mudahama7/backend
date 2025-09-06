package backend.service.business_logic;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Plainte;
import jakarta.mail.MessagingException;

import java.util.List;

public interface PlainteService {

    boolean createAffaire(NewAffaire newAffaire) throws MessagingException;

    Plainte findById(String id);

    List<AffaireDtoPourList> findAll();

    List<AffaireDtoPourList> filterAllAffairsPerStatus(String status);

    AffaireDetails findAllAffairDetails(String id);

    Boolean approbationAffaireGreffier(String idAffaire);

    Boolean approbationAffairePresident(String idAffaire);

}