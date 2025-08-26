package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class Bureau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelleBureau;

    @ManyToOne
    private Services service;

    @OneToMany(mappedBy = "bureau", cascade = CascadeType.ALL)
    private List<Utilisateur> utilisateurs;

}