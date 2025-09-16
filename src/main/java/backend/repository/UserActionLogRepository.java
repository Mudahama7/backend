package backend.repository;

import backend.model.log.UserActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionLogRepository extends JpaRepository<UserActionLog, Long> {
}
