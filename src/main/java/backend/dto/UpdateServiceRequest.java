package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateServiceRequest {
    private Long  id;
    private String new_libelle_service;
}