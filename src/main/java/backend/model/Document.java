package backend.model;

import backend.model.enums.CategoryDocument;
import backend.model.enums.DocumentKeyword;
import backend.model.enums.TypeDocument;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricule;
    private String nom_document;
    private String num_version;
    private String description;
    private String nature_document;

    @ManyToOne
    private Utilisateur utilisateur;

    private LocalDate date_creattion =  LocalDate.now();

    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    @Enumerated(EnumType.STRING)
    private List<DocumentKeyword> documentKeyword;

    @Enumerated(EnumType.STRING)
    private CategoryDocument  categoryDocument;

    @ManyToOne
    private Affaire_Ou_Dossier  affaire_ou_dossier;

}