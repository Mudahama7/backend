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
    private Long id;

    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String profession;
    private LocalDate dateNaissance;
    private String profile;
    private String function;
    private String phone;
    private char sex;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Bureau bureau = null;

    @OneToMany(mappedBy = "utilisateur",  cascade = CascadeType.ALL)
    private List<Document> documents;

}