package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.repository.HistoriquePartageDuDossierRepository;
import backend.service.business_logic.PartageAffaireService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.PartageDossierMapper;
import backend.service.utils.ConnectedUserGetter;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class PartageAffaireServiceImpl implements PartageAffaireService {

    private final HistoriquePartageDuDossierRepository historiquePartageDuDossierRepository;
    private final PartageDossierMapper partageDossierMapper;
    private final UtilisateurService utilisateurService;
    private final EmailService emailService;
    private final ConnectedUserGetter connectedUserGetter;

    @Override
    public boolean shareAffaire(NewSharingAffaireRequest newSharingAffaireRequest, Utilisateur sharingDossierAuthor, Plainte concernedAffair) throws IOException, MessagingException {

        Utilisateur destinataire = utilisateurService.getUserByNom(newSharingAffaireRequest.getNomDestinataire());

        HistoriquePartageDuDossier newShare = partageDossierMapper.mapFromNewShareToHistory(newSharingAffaireRequest, sharingDossierAuthor, concernedAffair);
        historiquePartageDuDossierRepository.save(newShare);

        String msgText = String.format(
                "Bonjour %s,\n\n" +
                        "Vous avez reçu un nouveau partage de dossier de la part de %s.\n" +
                        "Merci de consulter le dossier via votre espace de travail.\n\n" +
                        "Cordialement,\nL'équipe.",
                connectedUserGetter.getConnectedUser().getNomComplet()
        );

        emailService.sendEmail(destinataire.getEmail(), msgText, "Nouveau partage de dossier");

        return true;
    }

    @Override
    public List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom) {
        return historiquePartageDuDossierRepository.findAllByNomDestinataire(nom);
    }

}
