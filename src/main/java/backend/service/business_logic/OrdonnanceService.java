package backend.service.business_logic;

import backend.dto.newEntityRequest.NewOrdonnance;
import backend.model.Ordonnance;

import java.io.IOException;

public interface OrdonnanceService {

    boolean verifyIfSignaturesExist();

    boolean creerOrdonnance(NewOrdonnance data) throws Exception;

    boolean signerOrdonnance(String idOrdonnance) throws Exception;

    Ordonnance findById(String idOrdonnance);

}
