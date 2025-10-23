package backend.service.mapper;

import backend.dto.SharedAffairesDansNotifications;
import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.dto.subObjects.HistoriquePartageDossierDto;
import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.service.utils.FileGenerator;
import backend.service.utils.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class PartageDossierMapper {

    private final FileGenerator fileGenerator;
    private final SpringTemplateEngine templateEngine;
    private final FileMapper fileMapper;
    private final MinioService minioService;

    public HistoriquePartageDuDossier mapFromNewShareToHistory(
            NewSharingAffaireRequest newShare,
            Utilisateur sharingDossierAuthor,
            Plainte sharedDossier) throws Exception {

        HistoriquePartageDuDossier newPartage =  new HistoriquePartageDuDossier();

        byte[] fileGenerated = getPdfFileFromOurTemplate(
                sharingDossierAuthor.getNomComplet(),
                newShare.getNomDestinataire(),
                sharedDossier.getPlaignant().getNom()+" vs "+sharedDossier.getDefendeur().getNom(),
                sharingDossierAuthor.getSignatureUrlImage()
        );

        String fileName = "note_de_partage_numero "+generateSharingUniqueKey();

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(fileGenerated, fileName);

        String storedFileUrl = minioService.uploadFile(mappedFile);

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


        context.setVariable("logo", "http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3BpZWNlcy1qb2ludGVzLXNnZWRqLXN5c3RlbS9sb2dvLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPVAxNDVHMkFHSjVPWlRUSTQ1TVRWJTJGMjAyNTEwMjMlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMDIzVDEzMDcxNFomWC1BbXotRXhwaXJlcz00MzIwMCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSlFNVFExUnpKQlIwbzFUMXBVVkVrME5VMVVWaUlzSW1WNGNDSTZNVGMyTVRJMk16QTVPQ3dpY0dGeVpXNTBJam9pYldSb0luMC5rOVhxZWdNam1lajJ3cUd1Qm85S1l1SHE0WlVCU3k0X2wzb1BFelRJak10Qmx2Wk5FNXlHR1RtX1ZmOTQxMzRqRTFQMngxd0MzdXp0U1ZLVkZlNzZtUSZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPTg0ODBkNGM0ZTFmN2EzMmFlYWIyZGJlNDVmZDZjM2UxOGUxOGVhY2Q2Y2JiYjAzZDcyZjlmM2I1YTdmZjVlNDk");
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


    private String generateSharingUniqueKey(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder keyWord = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * chars.length());
            keyWord.append(chars.charAt(index));
        }
        return keyWord.toString();
    }


}