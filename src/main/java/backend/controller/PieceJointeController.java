package backend.controller;

import backend.dto.newEntityRequest.NewPieceJointe;
import backend.service.business_logic.PieceJointeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/piece_jointe/")
public class PieceJointeController {


    private final PieceJointeService pieceJointeService;

    @PreAuthorize("hasAuthority('acquisition_document')")
    @PostMapping("nouvelle_piece/")
    public ResponseEntity<?> nouvellePiece(
            @RequestParam String idAffaire,
            @RequestParam String nomFile,
            @RequestParam String typePiece,
            @RequestParam MultipartFile multipartFile
            ) throws IOException {
        NewPieceJointe reqBody = NewPieceJointe.builder()
                .idAffaire(idAffaire)
                .nomFile(nomFile)
                .typePiece(typePiece)
                .multipartFile(multipartFile)
                .build();

        return ResponseEntity.ok(pieceJointeService.joinPieceToAffaire(reqBody));

    }


}
