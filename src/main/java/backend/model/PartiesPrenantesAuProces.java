package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class PartiesPrenantesAuProces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String identifiantLegal;

    @OneToMany(mappedBy = "plaignant", cascade = CascadeType.ALL)
    private List<Plainte> plaintesCommePlaignant;

    @OneToMany(mappedBy = "defendeur", cascade = CascadeType.ALL)
    private List<Plainte> plaintesCommeDefendeur;

}