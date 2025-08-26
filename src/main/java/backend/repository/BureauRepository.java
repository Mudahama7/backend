package backend.repository;

import backend.model.Bureau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BureauRepository extends JpaRepository<Bureau, Long> {

    Optional<Bureau> findByLibelleBureau(String libelle_bureau);

}