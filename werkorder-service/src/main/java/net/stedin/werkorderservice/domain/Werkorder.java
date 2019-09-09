package net.stedin.werkorderservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@ToString
@Entity
public class Werkorder extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "werkorder_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "werkorder_SEQ")
    public Long id;

    public Long klantId;
    public Long monteurId;

    @Enumerated(EnumType.STRING)
    public WerkorderStatus status;
    public String aangemaaktDoor;

    @JsonFormat(shape = Shape.STRING)
    public LocalDate aanmaakDatum;
    public String omschrijving;
    public String commentaar;

    @Embedded
    public Adres adres;
}

