package backend.repository;

import backend.model.Utilisateur;
import backend.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.role <> backend.model.enums.Role.ADMINISTRATOR")
    List<Utilisateur> findAllUsers();

    Optional<Utilisateur> findByRole(Role role);

    Optional<Utilisateur> findByNomComplet(String nomComplet);

}