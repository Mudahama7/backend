package backend.repository;

import backend.model.HistoriquePartageDuDossier;
import backend.model.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriquePartageDuDossierRepository extends JpaRepository<HistoriquePartageDuDossier, Integer> {

    List<HistoriquePartageDuDossier> findAllByNomDestinataire(String nom);

    List<HistoriquePartageDuDossier> findAllByAffaireShared(Plainte plainte);

}
