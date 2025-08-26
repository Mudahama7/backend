package backend.service.contract;

import backend.dto.NewDepartementRequest;
import backend.dto.UpdateDepartementRequest;

public interface DepartementService {

    boolean createDepartement(NewDepartementRequest newDepartementRequest);

    boolean updateDepartement(UpdateDepartementRequest updateDepartementRequest);

}