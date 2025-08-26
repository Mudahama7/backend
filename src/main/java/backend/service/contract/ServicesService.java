package backend.service.contract;

import backend.dto.newEntityRequest.NewService;
import backend.dto.updateEntityRequest.UpdateDepartement;

public interface ServicesService {

    boolean createServices(NewService newService);

    boolean updateServices(UpdateDepartement updateDepartement);

}