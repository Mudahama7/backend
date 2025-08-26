package backend.service.contract;

import backend.dto.NewBureauRequest;
import backend.dto.UpdateBureauRequest;
import backend.model.Bureau;

public interface BureauService {

    boolean createBureau(NewBureauRequest newBureauRequest);

    boolean updateBureau(UpdateBureauRequest updateBureauRequest);

    Bureau findByLibelleBureau(String libelle_bureau);

}