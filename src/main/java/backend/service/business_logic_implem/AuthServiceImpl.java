package backend.service.business_logic_implem;

import backend.dto.auth.LoginRequest;
import backend.dto.auth.LoginResponse;
import backend.dto.auth.ResetPasswordRequest;
import backend.model.Utilisateur;
import backend.model.auth.ConnectedUser;
import backend.service.business_logic.AuthService;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.AuthMapper;
import backend.service.utils.EmailService;
import backend.service.utils.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotDePasse())
        );
        ConnectedUser connectedUser = (ConnectedUser) authentication.getPrincipal();

        System.out.println(connectedUser.getPassword());
        System.out.println(connectedUser.getEmailAsUsername());

        Utilisateur user = utilisateurService.getUtilisateurByEmail(loginRequest.getEmail());

        return authMapper.mapToLoginResponse(user, jwtUtil.generateToken(connectedUser));

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
            return true;
        }else {
            return false;
        }
    }

}