package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SharedAffairesDansNotifications {

    private String idAffair;
    private String nomAffair;
    private String datePartage;

}
