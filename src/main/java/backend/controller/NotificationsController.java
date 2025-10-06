package backend.controller;

import backend.dto.AffaireDetails;
import backend.dto.SharedAffairesDansNotifications;
import backend.service.business_logic.PartageAffaireService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications/")
@AllArgsConstructor
public class NotificationsController {


    private final PartageAffaireService partageAffaireService;


    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("get_all/")
    private ResponseEntity<List<SharedAffairesDansNotifications>> getAllNotifications()
    {
        return ResponseEntity.ok(partageAffaireService.findAllMyUnreadSharedAffairs());
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("view_details/{idAffair}/")
    private ResponseEntity<AffaireDetails>  getAffaireDetails(@PathVariable String idAffair)
    {
        return ResponseEntity.ok(partageAffaireService.viewSharedAffair(idAffair));
    }

    @PreAuthorize("hasAuthority('consulter_affaire')")
    @GetMapping("get_amount/")
    private ResponseEntity<Integer> getAmount()
    {
        return ResponseEntity.ok(partageAffaireService.findAllMyUnreadSharedAffairs().size());
    }

}