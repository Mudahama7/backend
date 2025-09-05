package backend.service.business_logic;

import backend.dto.newEntityRequest.NewJugementFinal;

public interface JugementService {

    boolean prononcerUnJugement(NewJugementFinal newJugementFinal);

}