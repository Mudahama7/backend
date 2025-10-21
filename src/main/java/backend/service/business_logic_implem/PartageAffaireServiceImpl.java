package backend.service.business_logic_implem;

import backend.dto.AffaireDetails;
import backend.dto.DashboardNeeds;
import backend.dto.SharedAffairesDansNotifications;
import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.model.enums.StatutDossier;
import backend.repository.HistoriquePartageDuDossierRepository;
import backend.service.business_logic.PartageAffaireService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.PartageDossierMapper;
import backend.service.mapper.PlainteMapper;
import backend.service.utils.ConnectedUserGetter;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class PartageAffaireServiceImpl implements PartageAffaireService {

    private final HistoriquePartageDuDossierRepository historiquePartageDuDossierRepository;
    private final PartageDossierMapper partageDossierMapper;
    private final UtilisateurService utilisateurService;
    private final EmailService emailService;
    private final ConnectedUserGetter connectedUserGetter;
    private final PlainteMapper plainteMapper;

    @Override
    public boolean shareAffaire(NewSharingAffaireRequest newSharingAffaireRequest, Utilisateur sharingDossierAuthor, Plainte concernedAffair) throws Exception {

        Utilisateur destinataire = utilisateurService.getUserByNom(newSharingAffaireRequest.getNomDestinataire());

        HistoriquePartageDuDossier newShare = partageDossierMapper.mapFromNewShareToHistory(newSharingAffaireRequest, sharingDossierAuthor, concernedAffair);
        historiquePartageDuDossierRepository.save(newShare);

        String msgText = String.format(
                """
                        Bonjour %s,
                        
                        Vous avez reçu un nouveau partage de dossier de la part de %s.
                        Merci de consulter le dossier via votre espace de travail.
                        
                        Cordialement,
                        L'équipe.""",
                newSharingAffaireRequest.getNomDestinataire(),
                connectedUserGetter.getConnectedUser().getNomComplet()
        );

        emailService.sendEmail(destinataire.getEmail(), msgText, "Nouveau partage de dossier");

        return true;
    }

    @Override
    public List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom) {
        return historiquePartageDuDossierRepository.findAllByNomDestinataire(nom);
    }

    @Override
    public List<SharedAffairesDansNotifications> findAllMyUnreadSharedAffairs() {
        Utilisateur user = connectedUserGetter.getConnectedUser();
        return historiquePartageDuDossierRepository.findAllUnreadSharedAffair(null, user.getNomComplet()).stream().map(partageDossierMapper::mapFromHistoryToNotification).toList();
    }

    @Override
    public AffaireDetails viewSharedAffair(String idAffair) {
        HistoriquePartageDuDossier story = historiquePartageDuDossierRepository.findById(Integer.parseInt(idAffair));
        story.setDateLectureDossierPartage(LocalDate.now());

        return plainteMapper.mapFromPlainteEntityToPlainteDetails(story.getAffaireShared());
    }

    @Override
    public DashboardNeeds provideDashboardNeeds() {

        Utilisateur connectedUser = connectedUserGetter.getConnectedUser();

        DashboardNeeds dashboardNeeds = new DashboardNeeds();

        dashboardNeeds.setTotalDossier(historiquePartageDuDossierRepository.findAllByNomDestinataire(connectedUser.getNomComplet()).size());
        dashboardNeeds.setEnCours(historiquePartageDuDossierRepository.findAllByStatusAndNomDestinataire(StatutDossier.EnCours, connectedUser.getNomComplet()).size());
        dashboardNeeds.setEnAttente(historiquePartageDuDossierRepository.findAllByStatusAndNomDestinataire(StatutDossier.EnAttente, connectedUser.getNomComplet()).size());
        dashboardNeeds.setJuger(historiquePartageDuDossierRepository.findAllByStatusAndNomDestinataire(StatutDossier.Juge, connectedUser.getNomComplet()).size());
        dashboardNeeds.setTopDixDerniersDossiers(
                historiquePartageDuDossierRepository
                        .findTo10ByNomDestinataireOrderByDatePartageDossierDesc(connectedUser.getNomComplet())
                        .stream()
                        .map(plainteMapper::mapFromHistoriqueDePartage)
                        .toList());

        return dashboardNeeds;
    }

}
