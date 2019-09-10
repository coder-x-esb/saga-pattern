package net.stedin.werkorderservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@ToString
public class Werkorder extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "werkorder_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "werkorder_SEQ")
    public Long id;

    @NotNull
    public Long klantId;

    @NotNull
    public Long monteurId;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    @Embedded
    public Adres adres;

    @Override
    @JsonIgnore
    public boolean isPersistent() {
        return super.isPersistent();
    }
}

