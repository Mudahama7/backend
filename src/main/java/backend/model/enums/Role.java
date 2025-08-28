package backend.model.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static backend.model.enums.Permission.*;

@AllArgsConstructor
@Getter
public enum Role {

    COMMON_PERMISSIONS(
            Sets.newHashSet(
                    INDEXER_DOCUMENT,
                    CLASSER_DOCUMENT,
                    IMPRIMER_DOCUMENT,
                    ROUTAGE_DOCUMENT,
                    CONSULTER_DOCUMENT,
                    MODIFIER_PROFIL,
                    CONSULTER_AFFAIRE
            )
    ),

    SECRETAIRE(
            Sets.newHashSet(
                    CREATE_AFFAIRE,
                    ACQUISITION_DOCUMENT,
                    MODIFIER_DOCUMENT,
                    SECURISER_DOCUMENT,
                    SUPPRIMER_DOCUMENT
            )
    ),

    PRESIDENT(
            Sets.newHashSet(
                    ACQUISITION_DOCUMENT,
                    MODIFIER_DOCUMENT,
                    VALIDER_DOCUMENT,
                    SECURISER_DOCUMENT,
                    SUPPRIMER_DOCUMENT
            )
    ),

    USER_PRIVILEGIE(
            Sets.newHashSet(
                    ACQUISITION_DOCUMENT
            )
    ),

    GREFFIER_DIV(
            Sets.newHashSet(
                    VALIDER_DOCUMENT,
                    SECURISER_DOCUMENT,
                    SUPPRIMER_DOCUMENT
            )
    ),

    ADMINISTRATOR(
            Sets.newHashSet(
                    MODIFIER_DOCUMENT,
                    SECURISER_DOCUMENT,
                    SUPPRIMER_DOCUMENT,
                    GERER_COMPTE_UTILISATEUR,
                    CONFIGURATION_SYSTEM,
                    CREER_ORDONNANCEMENT
            )
    );

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        authorities.addAll(
                COMMON_PERMISSIONS.getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toSet()));

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}