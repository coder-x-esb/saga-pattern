package net.stedin.medewerkerservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@ToString
@Builder
public class Medewerker {
    private Long id;
    private String voornaam;
    private String achternaam;
    @JsonFormat(pattern = "dd-MM-yyy", shape = STRING)
    private LocalDate geboorteDatum;
    private Functie functie;
    @JsonFormat(pattern = "dd-MM-yyy", shape = STRING)
    private LocalDate gereserveerdOp;
}
