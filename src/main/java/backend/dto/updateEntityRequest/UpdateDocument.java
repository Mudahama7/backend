package backend.dto.updateEntityRequest;

import lombok.Data;

@Data
public class UpdateDocument {

    private String matricule;
    private String nom_document;
    private int num_version;
    private String description;
    private String typeDocument;
    private String idDocument;

}