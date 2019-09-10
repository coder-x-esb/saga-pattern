package net.stedin.demo.saga.orchestrator.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medewerker {
    private Long id;
    private String voornaam;
    private String achternaam;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date geboorteDatum;
    private Functie functie;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date gereserveerdOp;
}
