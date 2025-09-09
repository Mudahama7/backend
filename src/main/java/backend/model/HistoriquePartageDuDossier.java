package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class HistoriquePartageDuDossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomDestinataire;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String resumeDossierPartage;

    private LocalDate datePartageDossier =  LocalDate.now();
    private LocalDate dateLectureDossierPartage;

    private String lienFichierSigne;

    @ManyToOne
    private Plainte affaireShared;

    @ManyToOne
    private Utilisateur auteurPartage;

}