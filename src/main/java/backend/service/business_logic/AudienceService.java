package backend.service.business_logic;

import backend.dto.SharingAudienceInfo;
import backend.dto.newEntityRequest.NewAudience;
import backend.model.Plainte;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface AudienceService {

    byte[] fixerNewAudience(NewAudience newAudience, Plainte concernedAffaire) throws IOException, MessagingException;

    void shareAudienceInformationAfterCreated(SharingAudienceInfo sharingAudienceInfo) throws MessagingException;

    Boolean verifyIfSignaturesExist();

}