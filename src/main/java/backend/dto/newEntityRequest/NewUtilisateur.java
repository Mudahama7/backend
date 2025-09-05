package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewUtilisateur {

    private String nomComplet;
    private String email;
    private String phone;
    private String role;

}
