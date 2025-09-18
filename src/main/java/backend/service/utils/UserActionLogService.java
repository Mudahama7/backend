package backend.service.utils;
import backend.model.log.UserActionLog;
import backend.repository.UserActionLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserActionLogService {
    private final UserActionLogRepository repository;

    public void logAction(String username, String method, String endpoint, String ip, String params) {
        UserActionLog log = new UserActionLog();
        log.setUsername(username != null ? username : "ANONYMOUS");
        log.setHttpMethod(method);
        log.setEndpoint(endpoint);
        log.setIpAddress(ip);
        log.setParameters(params);

        repository.save(log);
    }
}
