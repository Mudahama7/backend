package backend.dto.updateEntityRequest;

import lombok.Data;

@Data
public class UpdateUserPassword {

    String ancienMotDePasse;
    String nouveauMotDePasse;
    String confirmerMotDePasse;

}
