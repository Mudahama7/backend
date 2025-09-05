package backend.model;

import backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilisateur;
    private String nomComplet;
    private String email;
    private LocalDate dateCreationCompte =  LocalDate.now();
    private Role role;
    private String telephone;
    private String motDePasse;
    private String photoProfilUrl;
    private String signatureUrlImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ajouteePar")
    private List<PieceJointeAuDossier> toutesMesPieces;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auteurPartage")
    private List<HistoriquePartageDuDossier> historiquePartageDuDossiers;

}