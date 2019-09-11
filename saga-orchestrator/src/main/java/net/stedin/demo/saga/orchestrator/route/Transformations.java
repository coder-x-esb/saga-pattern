package net.stedin.demo.saga.orchestrator.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.stedin.demo.saga.orchestrator.route.domain.*;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Header;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transformations {

    ObjectMapper objectMapper = new ObjectMapper();

    public Planning transformToPlanWerkorder(@ExchangeProperty("Werkorder") Werkorder werkorder, SaveAndPlanWerkorder saveAndPlanWerkorder) {
        return Planning.builder()
            .medewerkerId(saveAndPlanWerkorder.getMedewerkerId())
            .werkorderId(werkorder.getId())
            .planningsDatum(saveAndPlanWerkorder.getDatum()).build();
    }

    public Medewerker transformToMedewerkerReservering(SaveAndPlanWerkorder saveAndPlanWerkorder) {
        return Medewerker.builder()
            .id(saveAndPlanWerkorder.getMedewerkerId())
            .gereserveerdOp(saveAndPlanWerkorder.getDatum()).build();
    }

    public Medewerker transformToMedewerkerVrijgeven(@Header("medewerkerId") Long medewerkerId) {
        return Medewerker.builder()
            .id(medewerkerId)
            .gereserveerdOp(null).build();
    }

    public Werkorder transformToWerkorder(SaveAndPlanWerkorder saveAndPlanWerkorder) {
        return Werkorder.builder()
                .omschrijving(saveAndPlanWerkorder.getWerkorder().omschrijving)
                .aangemaaktDoor("Henk")
                .aanmaakDatum(LocalDate.now())
                .status(WerkorderStatus.ACTIEF)
                .klantId(1L)
                .monteurId(saveAndPlanWerkorder.getMedewerkerId()).build();
    }

    public Werkorder transformToWerkorderAnnuleren(SaveAndPlanWerkorder saveAndPlanWerkorder) {
        return Werkorder.builder()
                .omschrijving(saveAndPlanWerkorder.getWerkorder().omschrijving)
                .aangemaaktDoor("Henk")
                .aanmaakDatum(LocalDate.now())
                .status(WerkorderStatus.GEANNULEERD)
                .klantId(1L)
                .monteurId(saveAndPlanWerkorder.getMedewerkerId()).build();
    }

    public String transformDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
