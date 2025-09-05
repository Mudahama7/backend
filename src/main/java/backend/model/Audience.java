package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Plainte idDossier;
    private String anneeAudience;
    private String jourAudience;
    private String moisAudience;
    private String dateAudience;
    private String heureAudience;
    private String causeInscrit;

}