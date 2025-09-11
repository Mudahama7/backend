package backend.service.business_logic_implem;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.model.enums.StatutDossier;
import backend.repository.PlainteRepository;
import backend.service.business_logic.PartageAffaireService;
import backend.service.business_logic.PlainteService;
import backend.service.mapper.PartageDossierMapper;
import backend.service.mapper.PlainteMapper;
import backend.service.utils.ConnectedUserGetter;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class PlainteServiceImpl implements PlainteService {

    private final PlainteRepository plainteRepository;
    private final PlainteMapper plainteMapper;
    private final EmailService emailService;
    private final ConnectedUserGetter connectedUserGetter;
    private final PartageAffaireService partageAffaireService;


    @Override
    public boolean createAffaire(NewAffaire newAffaire) throws MessagingException {

        Plainte plainte = plainteMapper.mapFromNewPlainteRequestToEntity(newAffaire);
        plainte.setDeposeChez(connectedUserGetter.getConnectedUser());

        plainteRepository.save(plainte);
        sendMailPourPrevenirLeConcerne(newAffaire.getDefendeur().getEmail(), newAffaire.getDefendeur().getNom());

        return true;
    }

    private void sendMailPourPrevenirLeConcerne(String email, String nom) throws MessagingException {

        String mailText = "Bonjour M. " + nom + ",\n\n"
                + "Par la présente, nous vous informons que vous avez été cité en qualité de défendeur "
                + "dans une affaire enregistrée auprès du Tribunal de Commerce de Goma.\n\n"
                + "Nous vous invitons à prendre connaissance de cette procédure et à vous préparer en conséquence.\n\n"
                + "Cordialement,\n"
                + "Le Greffe du Tribunal de Commerce de Goma";

        emailService.sendEmail(
                email,
                mailText,
                "Notification d'une plainte déposée à votre encontre"
        );

    }

    @Override
    public Plainte findById(String id) {
        return plainteRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AffaireDtoPourList> findAll() {
        return plainteRepository.findTousLesDossiersDeposeChezMoi(connectedUserGetter.getConnectedUser()).stream().map(plainteMapper::mapFromEntityToAffaireDtoList).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AffaireDtoPourList> findDossiersQuiMeSontPartages() {
        Utilisateur user = connectedUserGetter.getConnectedUser();

        return partageAffaireService.findAllByNomDestinataire(user.getNomComplet()).stream().map(plainteMapper::mapFromHistoriqueDePartage).toList();
    }

    @Override
    public List<AffaireDtoPourList> filterAllAffairsPerStatus(String status) {
        return plainteRepository.findAllByStatut(StatutDossier.valueOf(status)).stream().map(plainteMapper::mapFromEntityToAffaireDtoList).toList();
    }

    @Override
    public AffaireDetails findAllAffairDetails(String id) {
        return plainteMapper.mapFromPlainteEntityToPlainteDetails(findById(id));
    }

    @Override
    public Boolean approbationAffaireGreffier(String idAffaire) {
        Plainte plainte = findById(idAffaire);
        plainte.setValidationGreffier(true);
        plainteRepository.save(plainte);
        return true;
    }

    @Override
    public Boolean approbationAffairePresident(String idAffaire) {
        Plainte plainte = findById(idAffaire);
        plainte.setValidationPresident(true);
        plainteRepository.save(plainte);
        return true;
    }

}
