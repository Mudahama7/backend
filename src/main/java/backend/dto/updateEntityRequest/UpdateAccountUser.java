package backend.dto.updateEntityRequest;

import lombok.Data;

@Data
public class UpdateAccountUser {

    String nomComplet;
    String email;
    String phone;
    String role;

}
