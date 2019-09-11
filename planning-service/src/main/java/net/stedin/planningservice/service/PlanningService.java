package net.stedin.planningservice.service;

import net.stedin.planningservice.client.MedewerkerClient;
import net.stedin.planningservice.domain.Planning;
import net.stedin.planningservice.exception.PlanningException;
import net.stedin.planningservice.exception.PlanningNotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/planningen")
public class PlanningService {
    static final Logger log = LoggerFactory.getLogger(PlanningService.class);
    static List<Planning> planningen = new ArrayList<>();

    @Inject
    @RestClient
    MedewerkerClient medewerkerClient;

    @GET
    public List<Planning> findAll() {
        return planningen;
    }

    @GET
    @Path("/{id}")
    public Planning find(@PathParam("id") Long id) {
        return planningen.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new PlanningNotFoundException());
    }

    @POST
    public void save(Planning planning) {
        double i = ThreadLocalRandom.current().nextDouble();
        if (i >= 0.15) {
            String functie = this.medewerkerClient.find(planning.getMedewerkerId()).get("functie").asText();
            if ("monteur".equalsIgnoreCase(functie)) {
                Long id = (long) planningen.size() + 1;
                planning.setId(id);
                planningen.add(planning);
                this.log.debug("new planning added:\n" + planning);
            } else {
                throw new PlanningException("Medewerker is geen monteur");
            }
        } else {
            throw new PlanningException("Random expection occured :)");
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        boolean deleted = planningen.removeIf(p -> p.getId().equals(id));

        if (deleted) {
            log.debug("Planning(id={}) deleted", id);
        }
    }
}
