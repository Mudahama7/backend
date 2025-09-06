package backend.service.business_logic_implem;

import backend.dto.UserDtoPourList;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.dto.updateEntityRequest.UpdateAccountUser;
import backend.model.Utilisateur;
import backend.repository.UtilisateurRepository;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.UtilisateurMapper;
import backend.service.utils.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public boolean ajouter_user(NewUtilisateur user) throws MessagingException {
        Utilisateur newUser = utilisateurMapper.mapFromNewUserToUserEntity(user);

        String password = generatePassword();
        newUser.setMotDePasse(passwordEncoder.encode(password));

        utilisateurRepository.save(newUser);

        String mailText = "Bonjour, un compte pour acceder au système SGEDJ vous a été crée, vous pouvez vous connectez à partir de maintenant grace au mot de passe : "+password;
        emailService.sendEmail(
                user.getEmail(),
                mailText,
                "Confirmation de création de compte"
        );

        return true;
    }

    @Override
    public boolean supprimer_user(String idUser) {

        int id = Integer.parseInt(idUser);
        utilisateurRepository.deleteById(id);

        return true;
    }

    @Override
    public List<UserDtoPourList> getAll() {
        return utilisateurRepository.findAllUsers()
                .stream()
                .map(utilisateurMapper::mapFromUserToUserDtoList)
                .toList();
    }

    //TODO : implement this method

    @Override
    public boolean update_account(UpdateAccountUser updateAccountUser) {


        return true;
    }

    @Override
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
