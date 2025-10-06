package backend.controller;

import backend.dto.DashboardNeeds;
import backend.service.business_logic.PlainteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/")
public class DashboardController {

    private final PlainteService plainteService;

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("dashboard-elements/")
    public ResponseEntity<DashboardNeeds> provideDashboardElements(){
        return ResponseEntity.ok().body(plainteService.provideDashboardNeeds());
    }

}