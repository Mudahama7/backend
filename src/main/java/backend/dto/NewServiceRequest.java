package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewServiceRequest {
    private String libelle_service;
    private String libelle_departement;
}