package backend.service.contract;

import backend.dto.newEntityRequest.NewBureau;
import backend.dto.updateEntityRequest.UpdateBureau;
import backend.model.Bureau;

public interface BureauService {

    boolean createBureau(NewBureau newBureau);

    boolean updateBureau(UpdateBureau updateBureau);

    Bureau findByLibelleBureau(String libelle_bureau);

}