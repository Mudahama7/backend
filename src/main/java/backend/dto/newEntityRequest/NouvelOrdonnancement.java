package backend.dto.newEntityRequest;

import lombok.Data;

@Data
public class NouvelOrdonnancement {

    private String idAffair;
    private String jour_du_mois;
    private String mois;
    private String date_audience;
    private String cause_inscrite_sous;

}