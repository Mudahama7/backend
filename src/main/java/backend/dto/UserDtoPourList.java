package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDtoPourList {

    private int idUtilisateur;
    private String nomComplet;
    private String email;
    private String telephone;
    private String role;

}