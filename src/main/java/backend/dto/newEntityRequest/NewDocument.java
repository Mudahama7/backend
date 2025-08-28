package backend.dto.newEntityRequest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewDocument {

    private MultipartFile file;
    private String matricule;
    private String nom_document;
    private int num_version;
    private String description;
    private String typeDocument;
    private String idAffair;

}