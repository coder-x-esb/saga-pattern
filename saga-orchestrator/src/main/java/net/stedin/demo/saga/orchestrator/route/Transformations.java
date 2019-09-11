package net.stedin.demo.saga.orchestrator.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.stedin.demo.saga.orchestrator.route.domain.Medewerker;
import net.stedin.demo.saga.orchestrator.route.domain.PlanWerkorder;
import net.stedin.demo.saga.orchestrator.route.domain.SaveAndPlanWerkorder;
import net.stedin.demo.saga.orchestrator.route.domain.Werkorder;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Header;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transformations {

    ObjectMapper objectMapper = new ObjectMapper();

    public PlanWerkorder transformToPlanWerkorder(@ExchangeProperty("Werkorder") String werkorderString, SaveAndPlanWerkorder saveAndPlanWerkorder) throws IOException {
        Werkorder werkorder = objectMapper.readValue(werkorderString, Werkorder.class);
        return PlanWerkorder.builder()
            .medewerkerId(saveAndPlanWerkorder.getMedewerkerId())
            .werkorderId(werkorder.getId())
            .datum(saveAndPlanWerkorder.getDatum()).build();
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

    public String transformDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
