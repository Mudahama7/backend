package backend.config;

import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.UtilisateurRepository;
import backend.service.utils.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Configuration
public class AdminCreateAccount {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UtilisateurRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                Utilisateur user = new Utilisateur();
                user.setEmail("isharamudahama7@gmail.com");
                user.setNomComplet("Admin");
                user.setMotDePasse(passwordEncoder.encode("admin1234"));
                user.setRole(Role.ADMINISTRATOR);

                userRepository.save(user);

                emailService.sendEmail(
                        user.getEmail(),
                        "Compte admin crée avec succès, votre mot de passe est : admin1234",
                        "Création compte Administrator"
                );
            }
        };
    }
}
