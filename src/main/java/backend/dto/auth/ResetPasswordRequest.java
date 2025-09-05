package backend.dto.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String email;

}