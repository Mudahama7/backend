package backend.model;

import backend.model.enums.CategoryAffaire;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private CategoryAffaire categoryAffaire;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affaire_ou_dossier")
    private List<Document> documents;

}