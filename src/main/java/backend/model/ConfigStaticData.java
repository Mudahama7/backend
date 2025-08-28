package backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ConfigStaticData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name_president;
    private String url_image_president_signature;
    private String name_greffier_div;
    private String url_image_greffier_div_signature;
    private String url_image_logo_commune;

    private String versionConfig = "latest";

}
