package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle_service;

    @ManyToOne
    private Departement departement;

    @OneToMany(mappedBy = "service",  cascade = CascadeType.ALL)
    private List<Bureau> bureaux;

}