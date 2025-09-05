package backend.repository;

import backend.model.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudienceRepository extends JpaRepository<Audience, Integer> {
}