package backend.service.mapper;

import backend.dto.newEntityRequest.NewOrdonnance;
import backend.dto.subObjects.OrdonnancePourAffairDetails;
import backend.model.Ordonnance;
import backend.model.Plainte;
import org.springframework.stereotype.Service;

@Service
public class OrdonnanceMapper {


    public Ordonnance mapFromNewObjectToEntity(NewOrdonnance data, Plainte concernedAffair){
        Ordonnance ordonnance = new Ordonnance();

        ordonnance.setCorpsDocument(data.getCorsDocument());
        ordonnance.setPortantSur(data.getPortantSur());
        ordonnance.setConcernedAffair(concernedAffair);

        return ordonnance;
    }

    public NewOrdonnance mapFromEntityToObjectReq(Ordonnance data){
        return NewOrdonnance.builder()
                .idAffaire(String.valueOf(data.getConcernedAffair().getIdDossier()))
                .portantSur(data.getPortantSur())
                .corsDocument(data.getCorpsDocument())
                .build();
    }

    public OrdonnancePourAffairDetails mapFromEntityToOrdonnanceAffairDetails(Ordonnance data){
        return OrdonnancePourAffairDetails.builder()
                .id(String.valueOf(data.getId()))
                .creationDate(String.valueOf(data.getCreationDate()))
                .urlFile(String.valueOf(data.getUrlFile()))
                .numeroOrdonnance(String.valueOf(data.getNumeroOrdonnance()))
                .portantSur(data.getPortantSur())
                .signaturePresident(data.isSignaturePresident())
                .signatureGreffier(data.isSignatureGreffier())
                .build();
    }


}
