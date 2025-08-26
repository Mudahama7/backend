package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewDepartementRequest {
    private String libelle_departement;
}