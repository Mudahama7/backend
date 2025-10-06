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

    @Query("SELECT p FROM Plainte p WHERE p.statutDossier = :statut AND p.deposeChez = :user")
    List<Plainte> findAllByStatutAndDeposeChez(
            @Param("statut") StatutDossier statut,
            @Param("user") Utilisateur user
    );

    List<Plainte> findTop10ByDeposeChezOrderByDateDepotPlainteDesc(Utilisateur deposeChez);

    List<Plainte> findTop10ByOrderByDateDepotPlainteDesc();

}