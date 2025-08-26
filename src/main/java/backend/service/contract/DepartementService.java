package backend.service.contract;

import backend.dto.newEntityRequest.NewDepartement;
import backend.dto.updateEntityRequest.UpdateDepartement;

public interface DepartementService {

    boolean createDepartement(NewDepartement newDepartement);

    boolean updateDepartement(UpdateDepartement updateDepartement);

}