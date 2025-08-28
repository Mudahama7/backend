package backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DocumentDto {

    private String nom;
    private String description;
    private String url_file;

}
