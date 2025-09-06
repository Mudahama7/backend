package backend.repository;

import backend.model.Plainte;
import backend.model.enums.StatutDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlainteRepository extends JpaRepository<Plainte, Integer> {


    @Query("SELECT p FROM Plainte p WHERE p.statutDossier = :statut")
    List<Plainte> findAllByStatut(@Param("statut") StatutDossier statut);

}