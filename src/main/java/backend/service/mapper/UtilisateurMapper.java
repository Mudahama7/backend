package backend.service.mapper;

import backend.dto.UserDtoPourList;
import backend.dto.newEntityRequest.NewUtilisateur;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UtilisateurMapper {


    public Utilisateur mapFromNewUserToUserEntity(NewUtilisateur newUtilisateur) {
        Utilisateur user =  new Utilisateur();

        user.setEmail(newUtilisateur.getEmail());
        user.setNomComplet(newUtilisateur.getNomComplet());
        user.setRole(Role.valueOf(newUtilisateur.getRole()));
        user.setTelephone(newUtilisateur.getPhone());

        return user;
    }

    public UserDtoPourList mapFromUserToUserDtoList(Utilisateur utilisateur) {
        return UserDtoPourList.builder()
                .idUtilisateur(utilisateur.getIdUtilisateur())
                .email(utilisateur.getEmail())
                .nomComplet(utilisateur.getNomComplet())
                .telephone(utilisateur.getTelephone())
                .role(String.valueOf(utilisateur.getRole()).toLowerCase())
                .build();
    }


}
