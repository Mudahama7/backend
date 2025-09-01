package backend.model;

import backend.model.enums.CategoryAffaire;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Affaire_Ou_Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelleAffaire;

    private boolean archived = false;

    private boolean approbation_affair = false;

    private Double frais_pour_cette_affaire = 0.0;

    private LocalDate date_debut = null, date_fin = null;

    @Enumerated(EnumType.STRING)
    private CategoryAffaire categoryAffaire;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affaire_ou_dossier")
    private List<Document> documents;

}