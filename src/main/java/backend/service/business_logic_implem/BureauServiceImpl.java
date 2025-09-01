package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.NewBureau;
import backend.dto.updateEntityRequest.UpdateBureau;
import backend.model.Bureau;
import backend.repository.BureauRepository;
import backend.service.business_logic.BureauService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BureauServiceImpl implements BureauService {

    private final BureauRepository bureauRepository;

    @Override
    public boolean createBureau(NewBureau newBureau) {
        return false;
    }

    @Override
    public boolean updateBureau(UpdateBureau updateBureau) {
        return false;
    }

    @Override
    public Bureau findByLibelleBureau(String libelle_bureau) {
        return bureauRepository.findByLibelleBureau(libelle_bureau).orElse(null);
    }

}
