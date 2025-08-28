package backend.service.implentation;

import backend.dto.DocumentDto;
import backend.dto.newEntityRequest.NewDocument;
import backend.dto.newEntityRequest.NouvelOrdonnancement;
import backend.dto.updateEntityRequest.UpdateDocument;
import backend.model.Affaire_Ou_Dossier;
import backend.model.ConfigStaticData;
import backend.model.Document;
import backend.repository.DocumentRepository;
import backend.service.contract.Affaire_DossierService;
import backend.service.contract.ConfigurerSystem;
import backend.service.contract.DocumentService;
import backend.service.contract.UserService;
import backend.service.mapper.DocumentMapper;
import backend.service.utils.GenerateurOrdonnancement;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentMapper documentMapper;
    private final UserService userService;
    private final DocumentRepository documentRepository;
    private final Affaire_DossierService  affaireDossierService;
    private final SpringTemplateEngine templateEngine;
    private final GenerateurOrdonnancement generateurOrdonnancement;
    private final ConfigurerSystem configurerSystem;

    @Override
    public boolean createDocument(NewDocument newDocument) throws Exception {

        Document document = documentMapper.mapFromNewDocumentRequestToNewDocument(newDocument);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        document.setUtilisateur(userService.findByEmail(email));
        documentRepository.save(document);

        return true;

    }

    @Override
    public String getUrlDocument(Long id) {
        return Objects.requireNonNull(documentRepository.findById(id).orElse(null)).getFile_url();
    }

    @Override
    public boolean updateDocument(UpdateDocument updateDocument) {
        Optional<Document> documentToUpgrade = documentRepository.findById(Long.parseLong(updateDocument.getIdDocument()));
        if (documentToUpgrade.isPresent()) {
            Document documentUpgrade = documentMapper.mapFromUpdateDocumentRequestToDocument(updateDocument, documentToUpgrade.get());
            documentRepository.save(documentUpgrade);
        }
        return false;
    }

    @Override
    public List<DocumentDto> getAllDocumentPerAffaire(Long idAffair) {
        return affaireDossierService.findEntityById(idAffair).getDocuments().stream().map(documentMapper::fromEntityToDocumentDto).toList();
    }

    @Override
    public byte[] genererOrdonnancementPdfFile(NouvelOrdonnancement ordonnancement) {

        // Affaire_Ou_Dossier affaire_concernee = affaireDossierService.findEntityById(Long.parseLong(ordonnancement.getIdAffair()));

        ConfigStaticData configStaticData = new ConfigStaticData();
        configStaticData.setName_president("Nom du président");
        configStaticData.setName_greffier_div("Nom du greffier");

        Context context = getContext(ordonnancement, configStaticData);

        String html = templateEngine.process("ordonnancement", context);
        return generateurOrdonnancement.genererOrdonnancementDeFixationDeDate(html);

    }

    @NotNull
    private static Context getContext(NouvelOrdonnancement ordonnancement, ConfigStaticData configStaticData) {
        Context context = new Context();
        context.setVariable("jour_du_mois", ordonnancement.getJour_du_mois());
        context.setVariable("mois", ordonnancement.getMois());
        context.setVariable("nom_du_president", configStaticData.getName_president());
        context.setVariable("nom_du_greffier", configStaticData.getName_greffier_div());
        context.setVariable("date_audience", ordonnancement.getDate_audience());
        context.setVariable("cause_inscrite_sous", ordonnancement.getCause_inscrite_sous());
        return context;
    }

}