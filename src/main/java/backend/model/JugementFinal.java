package backend.model;

import backend.model.enums.StatutJugement;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class JugementFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Plainte idDossier;

    private LocalDate dateJugement =  LocalDate.now();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String resumeDecision;

    private Double montantDomages;

    @Enumerated(EnumType.STRING)
    private StatutJugement statut;

    private String copieJugementScannee;

}
