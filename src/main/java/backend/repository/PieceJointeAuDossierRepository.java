package backend.repository;

import backend.model.PieceJointeAuDossier;
import backend.model.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeAuDossierRepository extends JpaRepository<PieceJointeAuDossier, Integer> {

    List<PieceJointeAuDossier> findAllByPlainte(Plainte plainte);

}
