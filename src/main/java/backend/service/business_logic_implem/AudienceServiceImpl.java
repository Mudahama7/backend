package backend.service.business_logic_implem;

import backend.dto.SharingAudienceInfo;
import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewAudience;
import backend.model.Audience;
import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.AudienceRepository;
import backend.service.business_logic.AudienceService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.AudienceMapper;
import backend.service.mapper.FileMapper;
import backend.service.utils.FileGenerator;
import backend.service.utils.EmailService;
import backend.service.utils.MinioService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@AllArgsConstructor
@Service
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final UtilisateurService utilisateurService;
    private final SpringTemplateEngine templateEngine;
    private final FileGenerator fileGenerator;
    private final AudienceMapper audienceMapper;
    private final EmailService emailService;
    private final MinioService minioService;
    private final FileMapper fileMapper;


    @Override
    public byte[] fixerNewAudience(NewAudience newAudience, Plainte concernedAffaire) throws Exception {
        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(newAudience, presidentSignatures, greffierSignatures);

        String html = templateEngine.process("audience_template", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        Audience audience = audienceMapper.fromDtoToEntity(newAudience, concernedAffaire);

        String fileName = "audience numero "+generateAudienceUniqueKey()+" pour le dossier "+newAudience.getIdPlainte();

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, fileName);

        String urlAudience = minioService.uploadFile(mappedFile);
        audience.setUrlFile(urlAudience);

        audienceRepository.save(audience);

        SharingAudienceInfo sharingAudienceInfo = audienceMapper.mapToSharingAudienceInfo(newAudience, greffierSignatures.getEmail(), concernedAffaire);
        shareAudienceInformationAfterCreated(sharingAudienceInfo);
        return generatedFile;
    }

    private Context getContext(NewAudience newAudience, UserSignatures presidentSignatures, UserSignatures greffierSignatures) {

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Context context = new Context();
        String urlPhoto = "http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3BpZWNlcy1qb2ludGVzLXNnZWRqLXN5c3RlbS9sb2dvLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUMyVDAzUVdZMU1OM0pGVDRHVlhMJTJGMjAyNTEwMjUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMDI1VDIzMjIzNFomWC1BbXotRXhwaXJlcz00MzE5OCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSkRNbFF3TTFGWFdURk5Uak5LUmxRMFIxWllUQ0lzSW1WNGNDSTZNVGMyTVRRM056WTJNaXdpY0dGeVpXNTBJam9pYldSb0luMC4zaDB5ZjZnekd6Z0pjY3lDUDl5bExhcTcxbkVBZzBqUXN2TUx4R3o0Wm95NmNXalp5bEgxYjdWOHdIVWl3T2V3dTN4LVRoYmhIc01nUUhmRWt0ZDE4USZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPWY4ZjliYTU5ZDIwNDVhOGIwNzgxMzlkMWU2ZGJkZWJiYTRiMWU0ZjljNWFiZWUyOGU1ZWY3ZGE5MTFlZGQ0NzI";


        context.setVariable("logo", urlPhoto);
        context.setVariable("annee", newAudience.getAnnee());
        context.setVariable("jour_du_mois", newAudience.getJour());
        context.setVariable("mois", newAudience.getMois());
        context.setVariable("heure_debut", newAudience.getHeureDebut());
        context.setVariable("nom_du_president", presidentSignatures.getUserName());
        context.setVariable("nom_du_greffier",  greffierSignatures.getUserName());
        context.setVariable("date_audience",  newAudience.getDateAudience());
        context.setVariable("cause_inscrite_sous", newAudience.getCauseInscrit());
        context.setVariable("signature_greffier",   greffierSignatures.getUserSignatureUrl());

        if (connectedUser.getRole().equals(Role.PRESIDENT)){
            context.setVariable("signature_president",   presidentSignatures.getUserSignatureUrl());
        }

        return context;
    }

    @Override
    public void shareAudienceInformationAfterCreated(SharingAudienceInfo info) throws MessagingException {

        String emailMessage = "Bonjour,\n\n" +
                "Nous vous informons qu’une audience relative au dossier « " + info.getNomDossier() + " » a été fixée.\n\n" +
                "📅 Date de l’audience : " + info.getDateAudience() + "\n" +
                "⏰ Heure de début : " + info.getHeureDebut() + "\n" +
                "🏛️ Lieu : Tribunal de Commerce de Goma\n\n" +
                "Nous vous prions de bien vouloir prendre les dispositions nécessaires afin d’être présents à la date et à l’heure indiquées.\n\n" +
                "Cordialement,\n" +
                "Tribunal de Commerce de Goma\n" +
                "Cabinet du Président";

        sendMessage(info.getEmailGreffier(), emailMessage);
        sendMessage(info.getEmailDefendeur(), emailMessage);
        sendMessage(info.getEmailPlaignant(), emailMessage);
        sendMessage(info.getEmailSecretaire(), emailMessage);

    }

    private void sendMessage(String adresseMail, String msgText) throws MessagingException {
        emailService.sendEmail(adresseMail, msgText, "Nouvelle Audience");
    }

    @Override
    public Boolean verifyIfSignaturesExist() {
        boolean isPresidentSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT) != null;
        boolean isGreffierSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV) != null;
        return (isPresidentSignaturesExist && isGreffierSignaturesExist);
    }

    @Override
    public Boolean signerOrdonnByPreso(String idAudience) throws Exception {

        Audience concernedAudience = audienceRepository.findById(Integer.parseInt(idAudience));
        NewAudience newAudience = audienceMapper.mapFromEntityToNewAudienceReq(concernedAudience);

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(newAudience, presidentSignatures, greffierSignatures);
        String html = templateEngine.process("audience_template", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        String fileName = "audience numero "+generateAudienceUniqueKey()+" pour le dossier "+newAudience.getIdPlainte();
        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, fileName);

        minioService.deleteFile(concernedAudience.getUrlFile());
        String newUrlAudience = minioService.uploadFile(mappedFile);

        concernedAudience.setSignedByThePresident(true);
        concernedAudience.setUrlFile(newUrlAudience);
        audienceRepository.save(concernedAudience);

        return true;
    }

    private String generateAudienceUniqueKey(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder keyWord = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * chars.length());
            keyWord.append(chars.charAt(index));
        }
        return keyWord.toString();
    }

}
