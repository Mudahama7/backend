package backend.model;

import backend.model.enums.StatutDossier;
import backend.model.enums.TypePieceJointe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class PieceJointeAuDossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Plainte plainte;

    @Enumerated(EnumType.STRING)
    private TypePieceJointe typePieceJointe;

    private String nomFichier;
    private String urlFichier;
    private LocalDate dateAjout = LocalDate.now();

    @ManyToOne
    private Utilisateur ajouteePar;

}