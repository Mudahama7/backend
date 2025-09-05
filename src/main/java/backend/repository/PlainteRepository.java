package backend.repository;

import backend.model.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainteRepository extends JpaRepository<Plainte, Integer> {
}
