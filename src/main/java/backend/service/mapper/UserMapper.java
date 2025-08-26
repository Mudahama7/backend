package backend.service.mapper;

import backend.dto.NewUserRequest;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.service.contract.BureauService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserMapper {

    private BureauService bureauService;


    public Utilisateur fromNewUserRequestToUser(NewUserRequest newUserRequest) {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail(newUserRequest.getEmail());
        utilisateur.setNom(newUserRequest.getNom());
        utilisateur.setBureau(bureauService.findByLibelleBureau(newUserRequest.getBureau()));
        utilisateur.setDate_naissance(newUserRequest.getDate_naissance());
        utilisateur.setPhone(newUserRequest.getPhone());
        utilisateur.setPrenom(newUserRequest.getPrenom());
        utilisateur.setFunction(newUserRequest.getFunction());
        utilisateur.setProfession(newUserRequest.getProfession());
        utilisateur.setProfile(newUserRequest.getProfile());
        utilisateur.setSex(newUserRequest.getSex());
        utilisateur.setRole(Role.valueOf(newUserRequest.getRole().toUpperCase()));

        return utilisateur;
    }

}