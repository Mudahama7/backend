package backend.service.business_logic;

import backend.dto.newEntityRequest.NewJugementFinal;
import backend.model.Plainte;

import java.io.IOException;

public interface JugementService {

    boolean prononcerUnJugement(NewJugementFinal newJugementFinal, Plainte concernedAffair) throws IOException;

}