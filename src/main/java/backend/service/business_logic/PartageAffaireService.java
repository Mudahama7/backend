package backend.service.business_logic;

import backend.dto.AffaireDetails;
import backend.dto.DashboardNeeds;
import backend.dto.SharedAffairesDansNotifications;
import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface PartageAffaireService {

    boolean shareAffaire(NewSharingAffaireRequest newSharingAffaireRequest, Utilisateur sharingDossierAuthor, Plainte concernedAffair) throws Exception;

    List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom);

    List<SharedAffairesDansNotifications> findAllMyUnreadSharedAffairs();

    AffaireDetails viewSharedAffair(String idAffair);

    DashboardNeeds provideDashboardNeeds();
}