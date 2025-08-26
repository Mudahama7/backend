package backend.service.implentation;

import backend.dto.LoginRequest;
import backend.dto.LoginResponse;
import backend.dto.ResetPasswordRequest;
import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import backend.service.contract.AuthService;
import backend.service.contract.UserService;
import backend.service.mapper.ConnectedUserMapper;
import backend.service.utils.CustomUserDetails;
import backend.service.utils.EmailService;
import backend.service.utils.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetails customUserDetails;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        ConnectedUser connectedUser = (ConnectedUser) customUserDetails.loadUserByUsername(loginRequest.getEmail());
        Utilisateur user = userService.findByEmail(loginRequest.getEmail());

        return LoginResponse.builder()
                .token(jwtUtil.generateToken(connectedUser))
                .role(user.getRole().toString())
                .fullname(user.getNom()+" "+user.getPrenom())
                .build();
    }

    @Override
    public boolean resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        Utilisateur user = userService.findByEmail(resetPasswordRequest.getEmail());
        if (user != null){
            String newPassword = userService.generatePassword();
            String mailText = "Bonjour, votre mot de passe a été réinitialisé avec succès, vous pouvez vous connectez maintenant grace au mot de passe suivant : " + newPassword;
            user.setPassword(passwordEncoder.encode(newPassword));
            emailService.sendEmail(
                    user.getEmail(),
                    mailText,
                    "Réinitialisation du mot de passe"
            );
        }
        return false;
    }
}