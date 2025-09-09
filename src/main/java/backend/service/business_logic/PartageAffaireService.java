package backend.service.business_logic;

import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface PartageAffaireService {

    boolean shareAffaire(NewSharingAffaireRequest newSharingAffaireRequest, Utilisateur sharingDossierAuthor, Plainte concernedAffair) throws IOException, MessagingException;

    List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom);

}