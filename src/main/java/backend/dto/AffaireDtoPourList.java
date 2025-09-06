package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AffaireDtoPourList {

    private String libelePlainte;
    private String statutPlainte;

}