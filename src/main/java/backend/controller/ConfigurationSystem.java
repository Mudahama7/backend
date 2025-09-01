package backend.controller;

import backend.dto.newEntityRequest.ConfigurerLesDonneesStatiques;
import backend.service.business_logic.ConfigurerSystem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/administrator/")
public class ConfigurationSystem {

    private final ConfigurerSystem configurerSystem;

    @PostMapping("configurer-donnees-system")
    @PreAuthorize("hasAuthority('configuration_system')")
    public ResponseEntity<Boolean>  configurerDonneesStatics(@RequestBody ConfigurerLesDonneesStatiques configData) throws Exception {
        return ResponseEntity.ok(configurerSystem.configurerDonneesStatics(configData));
    }

}