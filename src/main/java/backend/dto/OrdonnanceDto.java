package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdonnanceDto {

    String idOrdonnance;
    String numeroOrdonnance;
    String portantSur;
    String urlFile;
    boolean signatureGreffier;
    boolean signaturePresident;

}
