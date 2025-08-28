package backend.controller;

import backend.dto.DocumentDto;
import backend.dto.newEntityRequest.NewDocument;
import backend.dto.newEntityRequest.NouvelOrdonnancement;
import backend.dto.updateEntityRequest.UpdateDocument;
import backend.service.contract.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/documents/")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("creer-document")
    @PreAuthorize("hasAuthority('acquisition_document')")
    public ResponseEntity<Boolean> creerDocument(@RequestBody NewDocument document) throws Exception {
        return ResponseEntity.ok(documentService.createDocument(document));
    }

    @GetMapping("get-url/{id}")
    @PreAuthorize("hasAuthority('consulter_document')")
    public ResponseEntity<String> getUrl(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getUrlDocument(id));
    }

    @GetMapping("get-all-per-affair/{idAffair}")
    public ResponseEntity<List<DocumentDto>> getAllPerAffair(@PathVariable Long idAffair) {
        return ResponseEntity.ok(documentService.getAllDocumentPerAffaire(idAffair));
    }

    @PutMapping("update-document-indexes")
    @PreAuthorize("hasAuthority('modifier_document')")
    public ResponseEntity<Boolean> updateDocumentIndexes(@RequestBody UpdateDocument updateDocument){
        return ResponseEntity.ok(documentService.updateDocument(updateDocument));
    }

    @PostMapping("creer-ordonnancement")
    @PreAuthorize("hasAuthority('creer_ordonnancement')")
    public ResponseEntity<ByteArrayResource> creerOrdonnancement(@RequestBody NouvelOrdonnancement nouvelOrdonnancement){

        byte[] pdfBytes = documentService.genererOrdonnancementPdfFile(nouvelOrdonnancement);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ordonnancement-de-fixation-de-date-audience.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(new ByteArrayResource(pdfBytes));

    }


}