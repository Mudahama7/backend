package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewJugementFinal;
import backend.model.JugementFinal;
import backend.model.Plainte;
import backend.repository.JugementFinalRepository;
import backend.service.business_logic.JugementService;
import backend.service.mapper.JugementMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JugementServiceImpl implements JugementService {

    private final JugementFinalRepository  jugementFinalRepository;
    private final JugementMapper jugementMapper;

    @Override
    public boolean prononcerUnJugement(NewJugementFinal newJugementFinal, Plainte concernedAffair) throws Exception {
        JugementFinal jugementFinal = jugementMapper.mapFromNewJugementFinalReqToJugementFinal(newJugementFinal, concernedAffair);
        jugementFinalRepository.save(jugementFinal);
        return true;
    }
}
