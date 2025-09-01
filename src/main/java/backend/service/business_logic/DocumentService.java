package backend.service.business_logic;

import backend.dto.DocumentDto;
import backend.dto.newEntityRequest.NewDocument;
import backend.dto.newEntityRequest.NouvelOrdonnancement;
import backend.dto.updateEntityRequest.UpdateDocument;

import java.util.List;

public interface DocumentService {

    boolean createDocument(NewDocument newDocument) throws Exception;

    String getUrlDocument(Long id);

    boolean updateDocument(UpdateDocument updateDocument);

    List<DocumentDto> getAllDocumentPerAffaire(Long idAffair);

    byte[] genererOrdonnancementPdfFile(NouvelOrdonnancement html);
}