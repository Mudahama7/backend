package backend.repository;

import backend.model.Audience;
import backend.model.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudienceRepository extends JpaRepository<Audience, Integer> {

    List<Audience> findAllByIdDossier(Plainte plainte);

    Audience findById(int id);

}