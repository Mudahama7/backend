package backend.service.mapper;

import backend.dto.newEntityRequest.ConfigurerLesDonneesStatiques;
import backend.model.ConfigStaticData;
import backend.service.utils.MinIoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DonneesDeConfigurationMapper {

    private final MinIoService minIoService;

    public ConfigStaticData mapFromStaticsDataConfigRequestToEntity(ConfigurerLesDonneesStatiques configData) throws Exception {
        ConfigStaticData configStaticData = new ConfigStaticData();

        configStaticData.setName_greffier_div(configData.getName_greffier_div());
        configStaticData.setUrl_image_greffier_div_signature(minIoService.uploadFile(configData.getUrl_image_greffier_div_signature()));

        configStaticData.setName_president(configData.getName_president());
        configStaticData.setUrl_image_president_signature(minIoService.uploadFile(configData.getUrl_image_president_signature()));

        configStaticData.setUrl_image_logo_commune(minIoService.uploadFile(configData.getUrl_image_logo_commune()));
        return configStaticData;
    }

}
