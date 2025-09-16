package backend.service.mapper;

import backend.dto.SharedAffairesDansNotifications;
import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.dto.subObjects.HistoriquePartageDossierDto;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.service.utils.FileGenerator;
import backend.service.utils.SupabaseStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class PartageDossierMapper {

    private final FileGenerator fileGenerator;
    private final SupabaseStorageService supabaseStorageService;
    private final SpringTemplateEngine templateEngine;

    public HistoriquePartageDuDossier mapFromNewShareToHistory(
            NewSharingAffaireRequest newShare,
            Utilisateur sharingDossierAuthor,
            Plainte sharedDossier) throws IOException {

        HistoriquePartageDuDossier newPartage =  new HistoriquePartageDuDossier();

        byte[] fileGenerated = getPdfFileFromOurTemplate(
                sharingDossierAuthor.getNomComplet(),
                newShare.getNomDestinataire(),
                sharedDossier.getPlaignant().getNom()+" vs "+sharedDossier.getDefendeur().getNom(),
                sharingDossierAuthor.getSignatureUrlImage()
        );

        String fileName = "note_de_partage_numero "+newPartage.getId();

        String storedFileUrl = supabaseStorageService.uploadFile(
                fileGenerated,
                fileName,
                "application/pdf"
        );

        newPartage.setNomDestinataire(newShare.getNomDestinataire());
        newPartage.setResumeDossierPartage(newShare.getResumeDossierPartage());
        newPartage.setLienFichierSigne(storedFileUrl);
        newPartage.setAffaireShared(sharedDossier);
        newPartage.setAuteurPartage(sharingDossierAuthor);

        return newPartage;
    }

    public byte[] getPdfFileFromOurTemplate(
            String nameAuthor,
            String nameDestinataire,
            String nameDossier,
            String signatureAuthor
    ){
        Context context = new Context();

        context.setVariable("date_partage", LocalDate.now());
        context.setVariable("utilisateur_a", nameAuthor);
        context.setVariable("nom_dossier",nameDossier);
        context.setVariable("utilisateur_b", nameDestinataire);
        context.setVariable("signature_utilisateur_a", signatureAuthor);

        String html = templateEngine.process("note_de_partage_de_dossier", context);
        return fileGenerator.generateFileFromHtml(html);
    }

    public HistoriquePartageDossierDto mapFromEntityToDto(HistoriquePartageDuDossier story){
        return HistoriquePartageDossierDto.builder()
                .roleAuthor(story.getAuteurPartage().getRole().toString())
                .nomAuthor(story.getAuteurPartage().getNomComplet())
                .resumePartage(story.getResumeDossierPartage())
                .datePartage(story.getDatePartageDossier().toString())
                .urlNoteDePartage(story.getLienFichierSigne())
                .build();
    }

    public SharedAffairesDansNotifications mapFromHistoryToNotification(HistoriquePartageDuDossier story){
        return SharedAffairesDansNotifications.builder()
                .idAffair(String.valueOf(story.getAffaireShared().getIdDossier()))
                .nomAffair(story.getAffaireShared().getPlaignant().getNom()+" Vs "+story.getAffaireShared().getDefendeur().getNom())
                .datePartage(story.getDatePartageDossier().toString())
                .build();
    }

}