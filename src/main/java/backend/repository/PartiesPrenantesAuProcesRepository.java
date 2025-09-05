package backend.repository;

import backend.model.JugementFinal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartiesPrenantesAuProcesRepository extends JpaRepository<JugementFinal, Integer> {
}
