package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewService {
    private String libelle_service;
    private String libelle_departement;
}