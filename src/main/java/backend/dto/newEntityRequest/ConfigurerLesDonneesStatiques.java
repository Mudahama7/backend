package backend.dto.newEntityRequest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConfigurerLesDonneesStatiques {

    private String name_president;
    private MultipartFile url_image_president_signature;
    private String name_greffier_div;
    private MultipartFile url_image_greffier_div_signature;
    private MultipartFile url_image_logo_commune;

}