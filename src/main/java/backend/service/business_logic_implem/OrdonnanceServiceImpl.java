package backend.service.business_logic_implem;

import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewAudience;
import backend.dto.newEntityRequest.NewOrdonnance;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.OrdonnanceRepository;
import backend.service.business_logic.OrdonnanceService;
import backend.service.business_logic.UtilisateurService;
import backend.service.utils.FileGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Service
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final UtilisateurService utilisateurService;
    private final OrdonnanceRepository ordonnanceRepository;
    private final SpringTemplateEngine templateEngine;
    private final FileGenerator fileGenerator;

    @Override
    public boolean verifyIfSignaturesExist() {
        boolean isPresidentSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT) != null;
        boolean isGreffierSignaturesExist = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV) != null;
        return (isPresidentSignaturesExist && isGreffierSignaturesExist);
    }

    @Override
    public byte[] creerOrdonnance(NewOrdonnance data) {

        UserSignatures greffierSignatures = utilisateurService.getUserSignaturesUrl(Role.GREFFIER_DIV);
        UserSignatures presidentSignatures = utilisateurService.getUserSignaturesUrl(Role.PRESIDENT);

        Context context = getContext(data, LocalDate.now(), presidentSignatures, greffierSignatures);

        String html = templateEngine.process("ordonnance", context);

        return fileGenerator.generateFileFromHtml(html);

    }


    private Context getContext(NewOrdonnance data, LocalDate dateOrdonnance, UserSignatures presidentSignatures, UserSignatures greffierSignatures) {

        //Utilisateur connectedUser = utilisateurService.getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

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

//        if (connectedUser.getRole().equals(Role.PRESIDENT)){
//            context.setVariable("nomPresident", presidentSignatures.getUserName());
//            context.setVariable("signature_president",   presidentSignatures.getUserSignatureUrl());
//        }
//        else if (connectedUser.getRole().equals(Role.GREFFIER_DIV)){
//            context.setVariable("nomGreffier",  greffierSignatures.getUserName());
//            context.setVariable("signature_greffier",   greffierSignatures.getUserSignatureUrl());
//        }

        return context;

    }

}


