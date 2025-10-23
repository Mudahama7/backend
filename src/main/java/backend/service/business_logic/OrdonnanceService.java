package backend.service.business_logic;

import backend.dto.newEntityRequest.NewOrdonnance;

public interface OrdonnanceService {

    boolean verifyIfSignaturesExist();

    byte[] creerOrdonnance(NewOrdonnance data);

}
