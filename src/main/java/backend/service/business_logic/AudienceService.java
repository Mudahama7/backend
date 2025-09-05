package backend.service.business_logic;

import backend.dto.SharingAudienceInfo;
import backend.dto.newEntityRequest.NewAudience;

public interface AudienceService {

    boolean fixerNewAudience(NewAudience newAudience);

    boolean shareAudienceInformationAfterCreated(SharingAudienceInfo sharingAudienceInfo);

}