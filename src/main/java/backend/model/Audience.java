package backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Plainte idDossier;

    private LocalDate date_creation =  LocalDate.now();

    private String anneeAudience;
    private String jourAudience;
    private String moisAudience;
    private String dateAudience;
    private String heureAudience;
    private String causeInscrit;
    private String urlFile;
    private boolean signedByThePresident = false;

}