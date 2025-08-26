package backend.service.mapper;

import backend.dto.Affaire_DossierDto;
import backend.dto.newEntityRequest.NewAffaire;
import backend.model.Affaire_Ou_Dossier;
import backend.model.enums.CategoryAffaire;
import org.springframework.stereotype.Service;

@Service
public class AffaireMapper {

    public Affaire_Ou_Dossier fromRequestToObject(NewAffaire newAffaire){
        Affaire_Ou_Dossier affaire = new Affaire_Ou_Dossier();
        affaire.setCategoryAffaire(CategoryAffaire.valueOf(newAffaire.getCategorie_affaire()));
        affaire.setLibelleAffaire(newAffaire.getLibelle_affaire());

        return affaire;
    }

    public Affaire_DossierDto fromEntityToDto(Affaire_Ou_Dossier affaire){
        return Affaire_DossierDto.builder()
                .libelleAffaire(affaire.getLibelleAffaire())
                .id(affaire.getId())
                .categorieAffaire(affaire.getCategoryAffaire().toString())
                .build();
    }

}
