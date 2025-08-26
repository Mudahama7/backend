package backend.repository;

import backend.model.Affaire_Ou_Dossier;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Affaire_DossierRepository extends JpaRepository<Affaire_Ou_Dossier, Long> {

    @NotNull Optional<Affaire_Ou_Dossier> findById(@NotNull Long idAffaire);

    List<Affaire_Ou_Dossier> findAllByArchived(boolean isThisAffairAlreadyArchived);

}