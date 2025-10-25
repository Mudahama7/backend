package backend.service.business_logic;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.DashboardNeeds;
import backend.dto.newEntityRequest.NewAffaire;
import backend.dto.updateEntityRequest.UpdateAffaire;
import backend.model.Plainte;
import jakarta.mail.MessagingException;

import java.util.List;

public interface PlainteService {

    boolean createAffaire(NewAffaire newAffaire) throws MessagingException;

    Plainte findById(String id);

    List<AffaireDtoPourList> findAll();

    List<AffaireDtoPourList> findDossiersQuiMeSontPartages();

    AffaireDetails findAllAffairDetails(String id);

    Boolean approbationAffaireGreffier(String idAffaire);

    Boolean approbationAffairePresident(String idAffaire);

    DashboardNeeds provideDashboardNeeds();

    boolean updateAffaireInfo(UpdateAffaire updateAffaire);

    void jugerAffaire(Plainte concernedAffair);

    Boolean archiverAffaire(String idDossier);

    List<AffaireDtoPourList> findAllArchivedAffairs();

    Boolean supprimerAffaire(String idDossier);

    Boolean desarchiverAffaire(String idDossier);

    List<AffaireDtoPourList> filterAffairs(String jour, String mois, String annee, String ordre);
}