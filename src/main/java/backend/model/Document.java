package backend.model;

import backend.model.enums.TypeDocument;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file_url;

    private String matricule;
    private String nom_document;
    private int num_version;
    private String description;

    @ManyToOne
    private Utilisateur utilisateur;

    private LocalDate date_creattion =  LocalDate.now();

    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    @ManyToOne
    private Affaire_Ou_Dossier  affaire_ou_dossier;

}