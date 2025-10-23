package backend.service.business_logic_implem;

import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewOrdonnance;
import backend.dto.newEntityRequest.NewSharingAffaireRequest;
import backend.model.Ordonnance;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.OrdonnanceRepository;
import backend.service.business_logic.OrdonnanceService;
import backend.service.business_logic.PartageAffaireService;
import backend.service.business_logic.PlainteService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.FileMapper;
import backend.service.mapper.OrdonnanceMapper;
import backend.service.utils.EmailService;
import backend.service.utils.FileGenerator;
import backend.service.utils.MinioService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Service
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final UtilisateurService utilisateurService;
    private final PlainteService plainteService;
    private final OrdonnanceRepository ordonnanceRepository;
    private final SpringTemplateEngine templateEngine;
    private final FileGenerator fileGenerator;
    private final MinioService minioService;
    private final FileMapper fileMapper;
    private final OrdonnanceMapper ordonnanceMapper;
    private final EmailService emailService;
    private final PartageAffaireService partageAffaireService;

    @Override
    public boolean verifyIfSignaturesExist() {
        boolean isPresidentSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT) != null;
        boolean isGreffierSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV) != null;
        return (isPresidentSignaturesExist && isGreffierSignaturesExist);
    }

    @Override
    public boolean creerOrdonnance(NewOrdonnance data) throws Exception {

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(data, LocalDate.now(), presidentSignatures, greffierSignatures);
        String html = templateEngine.process("ordonnance", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, "fileName");
        String urlSavedFile = minioService.uploadFile(mappedFile);

        Ordonnance ordonnance = ordonnanceMapper.mapFromNewObjectToEntity(data, plainteService.findById(data.getIdPlainte()));
        ordonnance.setUrlFile(urlSavedFile);

        ordonnanceRepository.save(ordonnance);
        sendMessages(data);
        shareAffair(data);
        return true;

    }

    @Override
    public boolean signerOrdonnance(String idOrdonnance) throws Exception {

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Ordonnance concernedOrdonnance = findById(idOrdonnance);
        NewOrdonnance mappedOrdonnanceObject = ordonnanceMapper.mapFromEntityToObjectReq(concernedOrdonnance);

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(mappedOrdonnanceObject, concernedOrdonnance.getCreationDate(), presidentSignatures, greffierSignatures);
        String html = templateEngine.process("ordonnance", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, "fileName");
        minioService.deleteFile(concernedOrdonnance.getUrlFile());
        String newUrlFile = minioService.uploadFile(mappedFile);

        concernedOrdonnance.setUrlFile(newUrlFile);
        if (connectedUser.getRole().equals(Role.GREFFIER_DIV)) {
            concernedOrdonnance.setSignatureGreffier(true);
        }
        else if(connectedUser.getRole().equals(Role.PRESIDENT)) {
            concernedOrdonnance.setSignaturePresident(true);
        }
        ordonnanceRepository.save(concernedOrdonnance);

        return true;
    }

    @Override
    public Ordonnance findById(String idOrdonnance) {
        return ordonnanceRepository.findById(Integer.parseInt(idOrdonnance)).orElse(null);
    }




    private void shareAffair(NewOrdonnance data) throws Exception {

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        NewSharingAffaireRequest shareReq = buildSharingReqObject(data.getIdPlainte(), greffierSignatures.getUserName());
        partageAffaireService.shareAffaire(shareReq, connectedUser, plainteService.findById(data.getIdPlainte()));

        shareReq = buildSharingReqObject(data.getIdPlainte(), presidentSignatures.getUserName());
        partageAffaireService.shareAffaire(shareReq, connectedUser, plainteService.findById(data.getIdPlainte()));

    }

    private NewSharingAffaireRequest buildSharingReqObject(String idAffaire, String nameRecipient){
        return NewSharingAffaireRequest.builder()
                .idDossier(idAffaire)
                .nomDestinataire(nameRecipient)
                .resumeDossierPartage("Partage de dossier pour lecture")
                .build();
    }

    private void sendMessages(NewOrdonnance data) throws MessagingException {

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        String messageSubject = "Signature réquise pour l'Ordonnance n° "+data.getNumeroOrdonnance()+" (Affaire n° "+data.getIdPlainte()+")";
        String corpsNotification = getNotificationBody("Greffier", data);
        emailService.sendEmail(greffierSignatures.getEmail(), corpsNotification, messageSubject);

        corpsNotification = getNotificationBody("Président", data);
        emailService.sendEmail(presidentSignatures.getEmail(), corpsNotification, messageSubject);

    }

    private String getNotificationBody(String recipientName, NewOrdonnance data){
        return "Cher "+recipientName+",\n\n" +
                "Nous vous informons par la présente que l'ordonnance n° "+data.getNumeroOrdonnance()+", portant sur la nouvelle affaire enregistrée (Affaire n° "+data.getIdPlainte()+" ), a été rédigée et finalisée.\n\n" +
                "Ce document est prêt et attend votre signature pour être officiellement validé. Nous vous prions de bien vouloir procéder à son examen et à sa signature dans les plus brefs délais.\n\n" +
                "Cordialement,\n\n" +
                "[Votre Nom/Service]\n" +
                "Tribunal de Commerce";
    }

    private Context getContext(NewOrdonnance data, LocalDate dateOrdonnance, UserSignatures presidentSignatures, UserSignatures greffierSignatures) {

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        int jourDuMois = dateOrdonnance.getDayOfMonth();
        DateTimeFormatter formateurMois = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        String nomDuMois = dateOrdonnance.format(formateurMois);

        Context context = new Context();

        context.setVariable("logo", "http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3BpZWNlcy1qb2ludGVzLXNnZWRqLXN5c3RlbS9sb2dvLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPVAxNDVHMkFHSjVPWlRUSTQ1TVRWJTJGMjAyNTEwMjMlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMDIzVDEzMDcxNFomWC1BbXotRXhwaXJlcz00MzIwMCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSlFNVFExUnpKQlIwbzFUMXBVVkVrME5VMVVWaUlzSW1WNGNDSTZNVGMyTVRJMk16QTVPQ3dpY0dGeVpXNTBJam9pYldSb0luMC5rOVhxZWdNam1lajJ3cUd1Qm85S1l1SHE0WlVCU3k0X2wzb1BFelRJak10Qmx2Wk5FNXlHR1RtX1ZmOTQxMzRqRTFQMngxd0MzdXp0U1ZLVkZlNzZtUSZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPTg0ODBkNGM0ZTFmN2EzMmFlYWIyZGJlNDVmZDZjM2UxOGUxOGVhY2Q2Y2JiYjAzZDcyZjlmM2I1YTdmZjVlNDk");
        context.setVariable("numeroOrdonnance", data.getNumeroOrdonnance());
        context.setVariable("portantSur", data.getPortantSur());
        context.setVariable("jour", jourDuMois);
        context.setVariable("mois", nomDuMois);
        context.setVariable("corpsDocument", data.getCorpsDocument());

        if (connectedUser.getRole().equals(Role.PRESIDENT)){
            context.setVariable("nomPresident", presidentSignatures.getUserName());
            context.setVariable("signature_president",   presidentSignatures.getUserSignatureUrl());
        }
        else if (connectedUser.getRole().equals(Role.GREFFIER_DIV)){
            context.setVariable("nomGreffier",  greffierSignatures.getUserName());
            context.setVariable("signature_greffier",   greffierSignatures.getUserSignatureUrl());
        }

        return context;

    }

}


