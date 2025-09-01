package backend.service.business_logic;

import backend.dto.newEntityRequest.ConfigurerLesDonneesStatiques;
import backend.model.ConfigStaticData;

public interface ConfigurerSystem {

    boolean configurerDonneesStatics(ConfigurerLesDonneesStatiques configData) throws Exception;

    ConfigStaticData getLatestConfigStaticData();

}
