package backend.service.contract;

import backend.dto.NewServiceRequest;
import backend.dto.UpdateDepartementRequest;

public interface ServicesService {

    boolean createServices(NewServiceRequest newServiceRequest);

    boolean updateServices(UpdateDepartementRequest updateDepartementRequest);

}