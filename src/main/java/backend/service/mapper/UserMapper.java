package backend.service.mapper;

import backend.dto.newEntityRequest.NewUser;
import backend.model.Utilisateur;
import backend.model.enums.Role;
import backend.service.business_logic.BureauService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class UserMapper {

    private BureauService bureauService;

    public Utilisateur fromNewUserRequestToUser(NewUser newUser) {
        Utilisateur utilisateur = new Utilisateur();

//        utilisateur.setEmail(newUser.getEmail());
//        utilisateur.setNom(newUser.getNom());
//        utilisateur.setBureau(bureauService.findByLibelleBureau(newUser.getBureau()));
//        utilisateur.setDateNaissance(LocalDate.parse(newUser.getDate_naissance()));
//        utilisateur.setPhone(newUser.getPhone());
//        utilisateur.setPrenom(newUser.getPrenom());
//        utilisateur.setFunction(newUser.getFunction());
//        utilisateur.setProfession(newUser.getProfession());
//        utilisateur.setProfile(newUser.getProfile());
//        utilisateur.setSex(newUser.getSex());
//        utilisateur.setRole(Role.valueOf(newUser.getRole().toUpperCase()));

        return utilisateur;

    }

}