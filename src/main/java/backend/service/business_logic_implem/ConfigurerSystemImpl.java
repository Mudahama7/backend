package backend.service.business_logic_implem;

import backend.dto.newEntityRequest.ConfigurerLesDonneesStatiques;
import backend.model.ConfigStaticData;
import backend.repository.ConfigStaticDataRepository;
import backend.service.business_logic.ConfigurerSystem;
import backend.service.mapper.DonneesDeConfigurationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConfigurerSystemImpl implements ConfigurerSystem {

    private final ConfigStaticDataRepository configStaticDataRepository;
    private final DonneesDeConfigurationMapper donneesDeConfigurationMapper;

    @Override
    public boolean configurerDonneesStatics(ConfigurerLesDonneesStatiques configData) throws Exception {
        ConfigStaticData configStaticData = donneesDeConfigurationMapper.mapFromStaticsDataConfigRequestToEntity(configData);
        configStaticDataRepository.save(configStaticData);
        return true;
    }

    @Override
    public ConfigStaticData getLatestConfigStaticData() {
        return configStaticDataRepository.findByVersionConfig("latest").orElse(null);
    }
}
