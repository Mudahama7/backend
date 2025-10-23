package backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate creationDate = LocalDate.now();

    private String urlFile;

    @ManyToOne
    private Plainte concernedAffair;

    private String numeroOrdonnance;

    private String portantSur;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String corpsDocument;

    private boolean signatureGreffier = false;
    private boolean signaturePresident = false;

}
