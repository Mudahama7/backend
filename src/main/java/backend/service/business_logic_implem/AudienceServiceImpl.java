package backend.service.business_logic_implem;

import backend.dto.SharingAudienceInfo;
import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewAudience;
import backend.model.Audience;
import backend.model.Plainte;
import backend.model.enums.Role;
import backend.repository.AudienceRepository;
import backend.service.business_logic.AudienceService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.AudienceMapper;
import backend.service.utils.FileGenerator;
import backend.service.utils.EmailService;
import backend.service.utils.SupabaseStorageService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@AllArgsConstructor
@Service
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final UtilisateurService utilisateurService;
    private final SpringTemplateEngine templateEngine;
    private final FileGenerator fileGenerator;
    private final AudienceMapper audienceMapper;
    private final SupabaseStorageService supabaseStorageService;
    private final EmailService emailService;

    @Override
    public byte[] fixerNewAudience(NewAudience newAudience, Plainte concernedAffaire) throws IOException, MessagingException {
        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(newAudience, presidentSignatures, greffierSignatures);

        String html = templateEngine.process("audience_template", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        Audience audience = audienceMapper.fromDtoToEntity(newAudience, concernedAffaire);

        String fileName = "audience_numero_"+audience.getId()+"du dossier "+newAudience.getIdPlainte();

        String urlAudience = supabaseStorageService.uploadFile(generatedFile, fileName, "application/pdf");
        audience.setUrlFile(urlAudience);

        audienceRepository.save(audience);

        SharingAudienceInfo sharingAudienceInfo = audienceMapper.mapToSharingAudienceInfo(newAudience, greffierSignatures.getEmail(), concernedAffaire);
        shareAudienceInformationAfterCreated(sharingAudienceInfo);
        return generatedFile;
    }

    @NotNull
    private static Context getContext(NewAudience newAudience, UserSignatures presidentSignatures, UserSignatures greffierSignatures) {
        Context context = new Context();

        context.setVariable("annee", newAudience.getAnnee());
        context.setVariable("jour_du_mois", newAudience.getJour());
        context.setVariable("mois", newAudience.getMois());
        context.setVariable("heure_debut", newAudience.getHeureDebut());
        context.setVariable("nom_du_president", presidentSignatures.getUserName());
        context.setVariable("nom_du_greffier",  greffierSignatures.getUserName());
        context.setVariable("date_audience",  newAudience.getDateAudience());
        context.setVariable("cause_inscrite_sous", newAudience.getCauseInscrit());
        context.setVariable("signature_greffier",   greffierSignatures.getUserSignatureUrl());
        context.setVariable("signature_president",   presidentSignatures.getUserSignatureUrl());
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

}
