package backend.config;

import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.UserRepository;
import backend.service.utils.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@AllArgsConstructor
@Configuration
public class AdminCreateAccount {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                Utilisateur user = new Utilisateur();
                user.setEmail("isharamudahama7@gmail.com");
                user.setNom("Admin");
                user.setPassword(passwordEncoder.encode("admin1234"));
                user.setPrenom("Super");
                user.setProfession("Administrator");
                user.setDateNaissance(LocalDate.parse("1990-01-01"));
                user.setProfile("profile.png");
                user.setFunction("Responsible");
                user.setPhone("+243900000000");
                user.setSex('M');
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
