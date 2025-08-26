package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NewUser {

    private String email;
    private String nom;
    private String prenom;
    private String profession;
    private String date_naissance;
    private String profile;
    private String function;
    private String phone;
    private char sex;
    private String role;
    private String bureau;

}