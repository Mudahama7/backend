package backend.service.business_logic_implem;

import backend.dto.auth.LoginRequest;
import backend.dto.auth.LoginResponse;
import backend.dto.auth.ResetPasswordRequest;
import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import backend.service.business_logic.AuthService;
import backend.service.business_logic.UtilisateurService;
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

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetails customUserDetails;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotDePasse()));

        ConnectedUser connectedUser = (ConnectedUser) customUserDetails.loadUserByUsername(loginRequest.getEmail());
        Utilisateur user = utilisateurService.getUtilisateurByEmail(loginRequest.getEmail());

        return LoginResponse.builder()
                .nomComplet(user.getNomComplet())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .role(user.getRole().toString())
                .photoProfil(user.getPhotoProfilUrl())
                .token(jwtUtil.generateToken(connectedUser))
                .build();
    }

    @Override
    public boolean resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        Utilisateur user = utilisateurService.getUtilisateurByEmail(resetPasswordRequest.getEmail());
        if (user != null){
            String newPassword = utilisateurService.generatePassword();
            String mailText = "Bonjour, votre mot de passe a été réinitialisé avec succès, vous pouvez vous connectez maintenant grace au mot de passe suivant : " + newPassword;
            user.setMotDePasse(passwordEncoder.encode(newPassword));
            emailService.sendEmail(
                    user.getEmail(),
                    mailText,
                    "Réinitialisation du mot de passe"
            );
        }
        return false;
    }
}