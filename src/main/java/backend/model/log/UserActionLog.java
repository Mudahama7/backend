package backend.model.log;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class UserActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String httpMethod;
    private String endpoint;
    private String ipAddress;
    private String parameters;
    private LocalDateTime timestamp;

}
