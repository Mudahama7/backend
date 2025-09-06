package backend.service.business_logic_implem;

import backend.dto.AffaireDetails;
import backend.dto.AffaireDtoPourList;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Plainte;
import backend.model.enums.StatutDossier;
import backend.repository.PlainteRepository;
import backend.service.business_logic.PlainteService;
import backend.service.mapper.PlainteMapper;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PlainteServiceImpl implements PlainteService {

    private final PlainteRepository plainteRepository;
    private final PlainteMapper plainteMapper;
    private final EmailService emailService;

    @Override
    public boolean createAffaire(NewAffaire newAffaire) throws MessagingException {
        Plainte plainte = plainteMapper.mapFromNewPlainteRequestToEntity(newAffaire);

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

    @Override
    public List<AffaireDtoPourList> findAll() {
        return plainteRepository.findAll().stream().map(plainteMapper::mapFromEntityToAffaireDtoList).toList();
    }

    @Override
    public List<AffaireDtoPourList> filterAllAffairsPerStatus(String status) {
        return plainteRepository.findAllByStatut(StatutDossier.valueOf(status)).stream().map(plainteMapper::mapFromEntityToAffaireDtoList).toList();
    }

    @Override
    public AffaireDetails findAllAffairDetails(String id) {
        return null;
    }

    @Override
    public Boolean approbationAffaireGreffier(String idAffaire) {

        return null;
    }

    @Override
    public Boolean approbationAffairePresident(String idAffaire) {
        return null;
    }
}
