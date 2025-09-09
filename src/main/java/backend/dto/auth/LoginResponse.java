package backend.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String nomComplet;
    private String email;
    private String telephone;
    private String role;
    private String photoProfil;
    private String token;

}