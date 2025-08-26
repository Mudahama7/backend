package backend.service.implentation;

import backend.dto.NewBureauRequest;
import backend.dto.UpdateBureauRequest;
import backend.model.Bureau;
import backend.repository.BureauRepository;
import backend.service.contract.BureauService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BureauServiceImpl implements BureauService {

    private final BureauRepository bureauRepository;

    @Override
    public boolean createBureau(NewBureauRequest newBureauRequest) {
        return false;
    }

    @Override
    public boolean updateBureau(UpdateBureauRequest updateBureauRequest) {
        return false;
    }

    @Override
    public Bureau findByLibelleBureau(String libelle_bureau) {
        return bureauRepository.findByLibelleBureau(libelle_bureau).orElse(null);
    }

}
