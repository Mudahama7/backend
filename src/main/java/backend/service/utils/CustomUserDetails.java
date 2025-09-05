package backend.service.utils;

import backend.model.Utilisateur;
import backend.repository.UtilisateurRepository;
import backend.service.mapper.ConnectedUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetails implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final ConnectedUserMapper connectedUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return connectedUserMapper.toConnectedUser(utilisateur);
    }

}