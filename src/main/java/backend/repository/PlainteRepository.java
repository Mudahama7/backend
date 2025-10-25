package backend.repository;

import backend.model.Plainte;
import backend.model.Utilisateur;
import backend.model.enums.StatutDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlainteRepository extends JpaRepository<Plainte, Integer> {

    @Query("SELECT p FROM Plainte p WHERE p.statutDossier = :statut")
    List<Plainte> findAllByStatut(@Param("statut") StatutDossier statut);

    @Query("SELECT p FROM Plainte p WHERE p.deposeChez = :user")
    List<Plainte> findTousLesDossiersDeposeChezMoi(@Param("user")Utilisateur user);

    @Query("SELECT p FROM Plainte p WHERE p.deposeChez = :user AND p.statutDossier <> :archiveStatus")
    List<Plainte> findAllByDeposeChezAndStatutDossierNotArchive(
            @Param("user") Utilisateur user,
            @Param("archiveStatus") StatutDossier archiveStatus
    );

    @Query("SELECT p FROM Plainte p WHERE p.statutDossier = :statut AND p.deposeChez = :user")
    List<Plainte> findAllByStatutAndDeposeChez(
            @Param("statut") StatutDossier statut,
            @Param("user") Utilisateur user
    );

    @Query("SELECT p FROM Plainte p WHERE p.statutDossier <> :statut")
    List<Plainte> findAllUnArchivedAffairs(
            @Param("statut") StatutDossier statut
    );

    // 🔹 Filtrage par année (ordre croissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE EXTRACT(YEAR FROM p.dateDepotPlainte) = CAST(:annee AS int)
        ORDER BY p.dateDepotPlainte ASC
    """)
    List<Plainte> findByAnneeAsc(@Param("annee") String annee);

    // 🔹 Filtrage par année (ordre décroissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE EXTRACT(YEAR FROM p.dateDepotPlainte) = CAST(:annee AS int)
        ORDER BY p.dateDepotPlainte DESC
    """)
    List<Plainte> findByAnneeDesc(@Param("annee") String annee);


    // 🔹 Filtrage par mois (ordre croissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE TO_CHAR(p.dateDepotPlainte, 'MM/YYYY') = :mois
        ORDER BY p.dateDepotPlainte ASC
    """)
    List<Plainte> findByMoisAsc(@Param("mois") String mois);

    // 🔹 Filtrage par mois (ordre décroissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE TO_CHAR(p.dateDepotPlainte, 'MM/YYYY') = :mois
        ORDER BY p.dateDepotPlainte DESC
    """)
    List<Plainte> findByMoisDesc(@Param("mois") String mois);


    // 🔹 Filtrage par jour (ordre croissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE TO_CHAR(p.dateDepotPlainte, 'DD/MM/YYYY') = :jour
        ORDER BY p.dateDepotPlainte ASC
    """)
    List<Plainte> findByJourAsc(@Param("jour") String jour);

    // 🔹 Filtrage par jour (ordre décroissant)
    @Query(value = """
        SELECT p 
        FROM Plainte p 
        WHERE TO_CHAR(p.dateDepotPlainte, 'DD/MM/YYYY') = :jour
        ORDER BY p.dateDepotPlainte DESC
    """)
    List<Plainte> findByJourDesc(@Param("jour") String jour);

    List<Plainte> findTop10ByDeposeChezOrderByDateDepotPlainteDesc(Utilisateur deposeChez);

    List<Plainte> findTop10ByOrderByDateDepotPlainteDesc();

}