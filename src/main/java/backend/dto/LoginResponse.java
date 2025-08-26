package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String token;
    private String fullname;
    private String role;

}