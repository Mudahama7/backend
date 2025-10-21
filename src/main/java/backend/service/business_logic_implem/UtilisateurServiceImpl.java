package backend.service.business_logic_implem;

import backend.dto.UserDtoPourList;
import backend.dto.UserSignatures;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.dto.updateEntityRequest.UpdateAccountUser;
import backend.dto.updateEntityRequest.UpdateUserPassword;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.repository.UtilisateurRepository;
import backend.service.business_logic.UtilisateurService;
import backend.service.mapper.UtilisateurMapper;
import backend.service.utils.ConnectedUserGetter;
import backend.service.utils.EmailService;
import backend.service.utils.MinioService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final MinioService minioServce;

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public Utilisateur getUserByNom(String nom) {
        Optional<Utilisateur> user = utilisateurRepository.findByNomComplet(nom);
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

        if (utilisateurRepository.findById(id).isPresent()) {
            utilisateurRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<UserDtoPourList> getAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::mapFromUserToUserDtoList)
                .toList();
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

    @Override
    public UserSignatures getUserSignaturesUrl(Role role) {
        return utilisateurRepository.findByRole(role)
                .map(utilisateur -> UserSignatures.builder()
                        .userSignatureUrl(utilisateur.getSignatureUrlImage())
                        .userName(utilisateur.getNomComplet())
                        .email(utilisateur.getEmail())
                        .build())
                .orElse(null);
    }

    @Override
    public boolean verifyIfUserAlreadyExists(String email) {
        return getUtilisateurByEmail(email) != null;
    }

    @Override
    public boolean AddSignature(MultipartFile signature) throws Exception {

        String storedSignatureUrl = minioServce.uploadFile(signature);
        Utilisateur connectedUser = getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        connectedUser.setSignatureUrlImage(storedSignatureUrl);
        utilisateurRepository.save(connectedUser);

        return true;
    }

    @Override
    public Boolean updatePassword(UpdateUserPassword data) {

        Utilisateur connectedUser = getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        boolean isThisCurrentPasswordCorrect = passwordEncoder.matches(data.getAncienMotDePasse(), connectedUser.getMotDePasse());

        if (isThisCurrentPasswordCorrect && data.getNouveauMotDePasse().equals(data.getConfirmerMotDePasse())){
            connectedUser.setMotDePasse(passwordEncoder.encode(data.getNouveauMotDePasse()));
            utilisateurRepository.save(connectedUser);
        }

        return true;
    }

    @Override
    public Boolean updateProfilePicture(MultipartFile photoProfile) throws Exception {

        Utilisateur connectedUser = getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        String storedPictureUrl = minioServce.uploadFile(photoProfile);
        connectedUser.setPhotoProfilUrl(storedPictureUrl);
        utilisateurRepository.save(connectedUser);

        return true;
    }

    @Override
    public Boolean updateUserInfo(UpdateAccountUser data) {

        Utilisateur connectedUser = getUtilisateurByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        connectedUser.setRole(Role.valueOf(data.getRole()));
        connectedUser.setEmail(data.getEmail());
        connectedUser.setNomComplet(data.getNomComplet());
        connectedUser.setTelephone(data.getPhone());

        utilisateurRepository.save(connectedUser);

        return true;
    }

}