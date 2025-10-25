package backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class DashboardNeeds {

    private int totalDossier;
    private int enCours;
    private int depose;
    private int juger;
    private List<AffaireDtoPourList> topDixDerniersDossiers;

}
