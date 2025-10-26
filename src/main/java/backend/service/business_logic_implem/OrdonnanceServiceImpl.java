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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

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

        String tableLengthPlusOne = String.valueOf(ordonnanceRepository.count()+1);

        Ordonnance ordonnance = ordonnanceMapper.mapFromNewObjectToEntity(data, plainteService.findById(data.getIdAffaire()));
        ordonnance.setNumeroOrdonnance(tableLengthPlusOne);

        Context context = getContext(data, LocalDate.now(), presidentSignatures, greffierSignatures, tableLengthPlusOne, Optional.empty());
        String html = templateEngine.process("ordonnance", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, "fileName");
        String urlSavedFile = minioService.uploadFile(mappedFile);

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

        if (connectedUser.getRole().equals(Role.GREFFIER_DIV)) {
            concernedOrdonnance.setSignatureGreffier(true);
        }
        else if(connectedUser.getRole().equals(Role.PRESIDENT)) {
            concernedOrdonnance.setSignaturePresident(true);
        }

        Context context = getContext(mappedOrdonnanceObject, concernedOrdonnance.getCreationDate(), presidentSignatures, greffierSignatures, concernedOrdonnance.getNumeroOrdonnance(), Optional.of(concernedOrdonnance));
        String html = templateEngine.process("ordonnance", context);
        byte[] generatedFile = fileGenerator.generateFileFromHtml(html);

        MultipartFile mappedFile = fileMapper.mapFromByteArrayToMultipartFile(generatedFile, "fileName");
        minioService.deleteFile(concernedOrdonnance.getUrlFile());
        String newUrlFile = minioService.uploadFile(mappedFile);

        concernedOrdonnance.setUrlFile(newUrlFile);

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

        NewSharingAffaireRequest shareReq = buildSharingReqObject(data.getIdAffaire(), greffierSignatures.getUserName());
        partageAffaireService.shareAffaire(shareReq, connectedUser, plainteService.findById(data.getIdAffaire()));

        shareReq = buildSharingReqObject(data.getIdAffaire(), presidentSignatures.getUserName());
        partageAffaireService.shareAffaire(shareReq, connectedUser, plainteService.findById(data.getIdAffaire()));

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

        String messageSubject = "Signature réquise pour une ordonnance(Affaire n° "+data.getIdAffaire()+")";
        String corpsNotification = getNotificationBody("Greffier", data);
        emailService.sendEmail(greffierSignatures.getEmail(), corpsNotification, messageSubject);

        corpsNotification = getNotificationBody("Président", data);
        emailService.sendEmail(presidentSignatures.getEmail(), corpsNotification, messageSubject);

    }

    private String getNotificationBody(String recipientName, NewOrdonnance data){

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        return "Cher "+recipientName+",\n\n" +
                "Nous vous informons par la présente qu'une ordonnance, portant sur la nouvelle affaire enregistrée(Affaire n° "+data.getIdAffaire()+" ), a été rédigée et finalisée.\n\n" +
                "Ce document est prêt et attend votre signature pour être officiellement validé. Nous vous prions de bien vouloir procéder à son examen et à sa signature dans les plus brefs délais.\n\n" +
                "Cordialement,\n\n" +
                connectedUser.getNomComplet()+"-"+connectedUser.getRole()+"\n" +
                "Tribunal de Commerce";
    }

    private Context getContext(NewOrdonnance data, LocalDate dateOrdonnance, UserSignatures presidentSignatures, UserSignatures greffierSignatures, String numeroOrdonnance, Optional<Ordonnance> concernedOrdonnace) {

        Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        int jourDuMois = dateOrdonnance.getDayOfMonth();
        DateTimeFormatter formateurMois = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        String nomDuMois = dateOrdonnance.format(formateurMois);

        Context context = new Context();
        String urlPhoto = "http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3BpZWNlcy1qb2ludGVzLXNnZWRqLXN5c3RlbS9sb2dvLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUMyVDAzUVdZMU1OM0pGVDRHVlhMJTJGMjAyNTEwMjUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMDI1VDIzMjIzNFomWC1BbXotRXhwaXJlcz00MzE5OCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSkRNbFF3TTFGWFdURk5Uak5LUmxRMFIxWllUQ0lzSW1WNGNDSTZNVGMyTVRRM056WTJNaXdpY0dGeVpXNTBJam9pYldSb0luMC4zaDB5ZjZnekd6Z0pjY3lDUDl5bExhcTcxbkVBZzBqUXN2TUx4R3o0Wm95NmNXalp5bEgxYjdWOHdIVWl3T2V3dTN4LVRoYmhIc01nUUhmRWt0ZDE4USZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPWY4ZjliYTU5ZDIwNDVhOGIwNzgxMzlkMWU2ZGJkZWJiYTRiMWU0ZjljNWFiZWUyOGU1ZWY3ZGE5MTFlZGQ0NzI";

        context.setVariable("logo", urlPhoto);
        context.setVariable("numeroOrdonnance", numeroOrdonnance);
        context.setVariable("portantSur", data.getPortantSur());
        context.setVariable("jour", jourDuMois);
        context.setVariable("mois", nomDuMois);
        context.setVariable("corpsDocument", data.getCorsDocument());

        if (concernedOrdonnace.isPresent()) {
            Ordonnance ordonnance = concernedOrdonnace.get();
            if (connectedUser.getRole().equals(Role.PRESIDENT) || ordonnance.isSignaturePresident()) {
                System.out.println(ordonnance.isSignaturePresident());
                context.setVariable("nomPresident", presidentSignatures.getUserName());
                context.setVariable("signature_president", presidentSignatures.getUserSignatureUrl());
            }
            if (connectedUser.getRole().equals(Role.GREFFIER_DIV) || ordonnance.isSignatureGreffier()) {
                System.out.println(ordonnance.isSignatureGreffier());
                context.setVariable("nomGreffier", greffierSignatures.getUserName());
                context.setVariable("signature_greffier", greffierSignatures.getUserSignatureUrl());
            }

        }

        return context;

    }

}


