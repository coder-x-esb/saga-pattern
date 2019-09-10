package net.stedin.demo.saga.orchestrator.transformations;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.stedin.demo.saga.orchestrator.domain.PlanWerkorder;
import net.stedin.demo.saga.orchestrator.domain.SaveAndPlanWerkorder;
import net.stedin.demo.saga.orchestrator.domain.Werkorder;
import org.apache.camel.ExchangeProperty;

import java.io.IOException;

public class Transformations {

    ObjectMapper objectMapper = new ObjectMapper();

    public PlanWerkorder transformToPlanWerkorder(@ExchangeProperty("Werkorder") String werkorderString, SaveAndPlanWerkorder saveAndPlanWerkorder) throws IOException {
        Werkorder werkorder = objectMapper.readValue(werkorderString, Werkorder.class);
        return PlanWerkorder.builder()
                .medewerkerId(saveAndPlanWerkorder.getMedewerkerId())
                .werkorderId(werkorder.getId())
                .datum(saveAndPlanWerkorder.getDatum()).build();
    }
}
