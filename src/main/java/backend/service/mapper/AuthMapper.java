package backend.service.mapper;

import backend.dto.auth.LoginResponse;
import backend.model.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class AuthMapper {

    public LoginResponse mapToLoginResponse(Utilisateur user, String jwToken) {

        return LoginResponse.builder()
                .nomComplet(user.getNomComplet())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .role(user.getRole().toString())
                .photoProfil(user.getPhotoProfilUrl())
                .token(jwToken)
                .build();

    }

}
