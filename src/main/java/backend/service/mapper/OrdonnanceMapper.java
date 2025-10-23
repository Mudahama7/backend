package backend.service.mapper;

import backend.dto.newEntityRequest.NewOrdonnance;
import backend.model.Ordonnance;
import backend.model.Plainte;
import org.springframework.stereotype.Service;

@Service
public class OrdonnanceMapper {


    public Ordonnance mapFromNewObjectToEntity(NewOrdonnance data, Plainte concernedAffair){
        Ordonnance ordonnance = new Ordonnance();

        ordonnance.setNumeroOrdonnance(data.getNumeroOrdonnance());
        ordonnance.setCorpsDocument(data.getCorpsDocument());
        ordonnance.setPortantSur(data.getPortantSur());
        ordonnance.setConcernedAffair(concernedAffair);

        return ordonnance;
    }

    public NewOrdonnance mapFromEntityToObjectReq(Ordonnance data){
        return NewOrdonnance.builder()
                .idPlainte(String.valueOf(data.getConcernedAffair().getIdDossier()))
                .numeroOrdonnance(data.getNumeroOrdonnance())
                .portantSur(data.getPortantSur())
                .corpsDocument(data.getCorpsDocument())
                .build();
    }


}
