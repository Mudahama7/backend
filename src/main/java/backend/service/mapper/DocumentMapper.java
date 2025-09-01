package backend.service.mapper;

import backend.dto.DocumentDto;
import backend.dto.newEntityRequest.NewDocument;
import backend.dto.updateEntityRequest.UpdateDocument;
import backend.model.Document;
import backend.model.enums.TypeDocument;
import backend.service.business_logic.Affaire_DossierService;
import backend.service.utils.MinIoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DocumentMapper {

    private final MinIoService minIoService;
    private final Affaire_DossierService  affaireDossierService;

    public Document mapFromNewDocumentRequestToNewDocument(NewDocument  newDocument) throws Exception {

        Document document = new Document();
        document.setMatricule(newDocument.getMatricule());
        document.setNom_document(newDocument.getNom_document());
        document.setNum_version(newDocument.getNum_version());
        document.setDescription(newDocument.getDescription());
        document.setFile_url(minIoService.uploadFile(newDocument.getFile()));
        document.setTypeDocument(TypeDocument.valueOf(newDocument.getTypeDocument()));
        document.setAffaire_ou_dossier(affaireDossierService.findEntityById(Long.parseLong(newDocument.getIdAffair())));
        return document;

    }

    public Document mapFromUpdateDocumentRequestToDocument(UpdateDocument updateDocument, Document document) {

        document.setMatricule(updateDocument.getMatricule());
        document.setNom_document(updateDocument.getNom_document());
        document.setNum_version(updateDocument.getNum_version());
        document.setDescription(updateDocument.getDescription());
        document.setTypeDocument(TypeDocument.valueOf(updateDocument.getTypeDocument()));

        return document;
    }

    public DocumentDto fromEntityToDocumentDto(Document document) {
        return DocumentDto.builder()
                .nom(document.getNom_document())
                .description(document.getDescription())
                .url_file(document.getFile_url())
                .build();
    }

}