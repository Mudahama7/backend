package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSignatures {

    private String userName;
    private String userSignatureUrl;
    private String email;

}