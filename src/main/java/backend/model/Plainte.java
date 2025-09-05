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

    private LocalDate dateDepotPlainte = LocalDate.now();

    @ManyToOne
    private PartiesPrenantesAuProces plaignant;

    private String natureLitige;

    private Double caution;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descriptionDesFaits;

    private Double MontantLitige;
    private String autresParties;

    private boolean validationGreffier;
    private boolean validationPresident;

    @Enumerated(EnumType.STRING)
    private StatutDossier statutDossier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plainte")
    private List<PieceJointeAuDossier> pieceJointeAuDossiers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDossier")
    private List<Audience> listeDAudiences;

    @OneToOne(mappedBy = "idDossier")
    private JugementFinal jugementFinal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affaireShared")
    private List<HistoriquePartageDuDossier> historiquePartageDuDossiers;

}