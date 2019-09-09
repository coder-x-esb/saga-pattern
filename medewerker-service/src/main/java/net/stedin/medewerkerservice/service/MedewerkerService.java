package net.stedin.medewerkerservice.service;

import lombok.extern.slf4j.Slf4j;
import net.stedin.medewerkerservice.domain.Functie;
import net.stedin.medewerkerservice.domain.Medewerker;
import net.stedin.medewerkerservice.exceptions.MedewerkerNotFoundException;

import javax.ws.rs.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/medewerkers")
public class MedewerkerService {
    static List<Medewerker> medewerkers = new ArrayList<>();

    static {
        medewerkers.add(
                Medewerker.builder()
                        .voornaam("Alvin")
                        .achternaam("Kwekel")
                        .functie(Functie.MONTEUR)
                        .geboorteDatum(LocalDate.now())
                        .gereserveerdOp(LocalDate.now()).build());
    }

    @GET
    public List<Medewerker> findAll() {
        return medewerkers;
    }

    @GET
    @Path("/{id}")
    public Medewerker find(@PathParam("id") Long id) {
        return medewerkers.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MedewerkerNotFoundException());
    }

    @GET
    @Path("/{functie}")
    public List<Medewerker> findByFunctie(@PathParam("functie") Functie functie) {
        return medewerkers.stream()
                .filter(m -> m.getFunctie().equals(functie))
                .collect(toList());
    }

    @POST
    public void save(Medewerker medewerker) {
        Long id = (long) medewerkers.size() + 1;
        medewerker.setId(id);
        medewerkers.add(medewerker);
        this.log.debug("new medewerker added:\n" + medewerker);
    }

    @PUT
    public void update(@PathParam("id") Long id, Medewerker medewerkerUpdate) {
        Medewerker storedMedewerker = medewerkers.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MedewerkerNotFoundException());
        medewerkers.set(medewerkers.indexOf(storedMedewerker), medewerkerUpdate);
        medewerkers.add(medewerkerUpdate);
        this.log.debug("new medewerker added:\n" + medewerkerUpdate);
    }

    @DELETE
    public void delete(Long id) {
        medewerkers.removeIf(m -> m.getId().equals(id));
    }
}
