package backend.dto.newEntityRequest;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class NewPieceJointe {

    private String idAffaire;
    private String nomFile;
    private String typePiece;
    private MultipartFile multipartFile;

}