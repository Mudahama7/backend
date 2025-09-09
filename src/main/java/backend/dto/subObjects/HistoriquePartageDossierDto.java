package backend.dto.subObjects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HistoriquePartageDossierDto {

    private String roleAuthor;
    private String nomAuthor;
    private String resumePartage;
    private String datePartage;
    private String urlNoteDePartage;

}
