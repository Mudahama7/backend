package backend.repository;

import backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.role != 'ADMINISTRATOR'")
    List<Utilisateur> findAllUsers();

}
