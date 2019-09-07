package net.stedin.medewerkerservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@ToString
public class Medewerker {
    private Long id;
    private String voornaam;
    private String achternaam;
    
    @JsonFormat(pattern = "dd-MM-yyy", shape = STRING)
    private Date geboorteDatum;
    private Functie functie;
}
