package net.stedin.werkorderservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@ToString
@Entity
public class Werkorder extends PanacheEntity {
    public Long klantId;
    public Long monteurId;
    public WerkorderStatus status;
    public String aangemaaktDoor;
    
    @JsonFormat(shape = Shape.STRING)
    public LocalDate aanmaakDatum;
    public String omschrijving;
    public String commentaar;
    
    @Embedded
    public Adres adres;
}

