package backend.model;

import backend.model.enums.StatutDossier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Plainte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDossier;


    @ManyToOne
    private PartiesPrenantesAuProces plaignant;

    @ManyToOne
    private PartiesPrenantesAuProces defendeur;

    private String natureLitige;

    private Double caution;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descriptionDesFaits;

    private String autresParties;

    private LocalDate dateDepotPlainte = LocalDate.now();

    private Double montantLitige = 0.0;

    private boolean sharedWithGreffier = false;
    private boolean sharedWithPresident = false;

    private boolean validationGreffier = false;
    private boolean validationPresident = false;

    @Enumerated(EnumType.STRING)
    private StatutDossier statutDossier = StatutDossier.Depose;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plainte")
    private List<PieceJointeAuDossier> pieceJointeAuDossiers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDossier")
    private List<Audience> listeDAudiences;

    @OneToOne(mappedBy = "idDossier")
    private JugementFinal jugementFinal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affaireShared")
    private List<HistoriquePartageDuDossier> historiquePartageDuDossiers;

}