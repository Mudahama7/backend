package backend.repository;

import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import backend.model.enums.StatutDossier;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistoriquePartageDuDossierRepository extends JpaRepository<HistoriquePartageDuDossier, Integer> {

    List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom);

    @Query("SELECT story FROM HistoriquePartageDuDossier story " +
            "WHERE story.dateLectureDossierPartage = :dateLecture " +
            "AND story.nomDestinataire = :nomDestinataire")
    List<HistoriquePartageDuDossier> findAllUnreadSharedAffair(
            @Param("dateLecture") LocalDate dateLecture,
            @Param("nomDestinataire") String nomDestinataire
    );

    List<HistoriquePartageDuDossier> findTo10ByNomDestinataireOrderByDatePartageDossierDesc(String nomDestinataire);

    @Query("SELECT story FROM HistoriquePartageDuDossier story " +
            "WHERE story.affaireShared.statutDossier = :status " +
            "AND story.nomDestinataire = :nomDestinataire")
    List<HistoriquePartageDuDossier> findAllByStatusAndNomDestinataire(
            @Param("status") StatutDossier statutDossier,
            @Param("nomDestinataire") String nomDestinataire
    );

    HistoriquePartageDuDossier findById(int id);

}
