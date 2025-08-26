package backend.service.implentation;

import backend.dto.NewUserRequest;
import backend.model.Utilisateur;
import backend.repository.UserRepository;
import backend.service.contract.UserService;
import backend.service.mapper.UserMapper;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean ajouter_user(NewUserRequest user) throws MessagingException {

        Utilisateur new_utilisateur = userMapper.fromNewUserRequestToUser(user);

        String password = generatePassword();
        String mailText = "Bonjour, un compte pour acceder au système SGEDJ vous a été crée, vous pouvez vous connectez à partir de maintenant grace au mot de passe : "+password;

        emailSender.sendEmail(
                user.getEmail(),
                mailText,
                "Confirmation de création de compte"
        );
        new_utilisateur.setPassword(passwordEncoder.encode(password));
        userRepository.save(new_utilisateur);

        return true;

    }

    @Override
    public boolean supprimer_user(String idUser) {

        Long id = Long.parseLong(idUser);
        userRepository.deleteById(id);

        return true;
    }

    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

}