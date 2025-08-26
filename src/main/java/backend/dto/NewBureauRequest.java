package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewBureauRequest {

    private String libelle_bureau;
    private String libelle_service;

}