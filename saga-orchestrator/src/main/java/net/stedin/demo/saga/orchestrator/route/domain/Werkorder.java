package net.stedin.demo.saga.orchestrator.route.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Werkorder {

    public Long id;

    @NotNull
    public Long klantId;

    @NotNull
    public Long monteurId;

    @NotNull
    public WerkorderStatus status;

    @NotNull
    public String aangemaaktDoor;

    @NotNull
    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public LocalDate aanmaakDatum;

    @NotBlank
    public String omschrijving;
    public String commentaar;

    public Adres adres;
}

