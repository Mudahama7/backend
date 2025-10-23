package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.model.Ordonnance;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Integer> {
}
