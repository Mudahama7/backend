package backend.repository;

import backend.model.ConfigStaticData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigStaticDataRepository extends JpaRepository<ConfigStaticData, Long> {

    Optional<ConfigStaticData> findByVersionConfig(String versionConfig);

}
