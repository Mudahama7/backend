package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class OrdonnancePourAffairDetails {

    private String id;
    private String creationDate ;
    private String urlFile;
    private String numeroOrdonnance;
    private String portantSur;
    private boolean signatureGreffier;
    private boolean signaturePresident;

}
