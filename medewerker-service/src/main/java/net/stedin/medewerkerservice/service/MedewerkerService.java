package net.stedin.medewerkerservice.service;

import net.stedin.medewerkerservice.domain.Functie;
import net.stedin.medewerkerservice.domain.Medewerker;
import net.stedin.medewerkerservice.exceptions.MedewerkerIsNotAvailableException;
import net.stedin.medewerkerservice.exceptions.MedewerkerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/medewerkers")
public class MedewerkerService {
    static final Logger log = LoggerFactory.getLogger(MedewerkerService.class);
    static List<Medewerker> medewerkers = new ArrayList<>();

    static {
        medewerkers.add(
            Medewerker.builder()
                .id(1L)
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
    @Path("/functie/{functie}")
    public List<Medewerker> findByFunctie(@PathParam("functie") Functie functie) {
        return medewerkers.stream()
            .filter(m -> m.getFunctie().equals(functie))
            .collect(toList());
    }

    @POST
    public Medewerker save(Medewerker medewerker) {
        Long id = (long) medewerkers.size() + 1;
        medewerker.setId(id);
        medewerkers.add(medewerker);
        log.debug("new medewerker added:\n" + medewerker);
        return medewerker;
    }

    @PATCH
    @Path("/{id}")
    public void update(@PathParam("id") Long id, Medewerker medewerkerUpdate) {
        double i = ThreadLocalRandom.current().nextDouble();
        if (i >= 0.15) {
            Medewerker storedMedewerker = find(id);
            if (medewerkerUpdate.getFunctie() != null) {
                storedMedewerker.setFunctie(medewerkerUpdate.getFunctie());
            }
            storedMedewerker.setGereserveerdOp(medewerkerUpdate.getGereserveerdOp());
            if (medewerkerUpdate.getGeboorteDatum() != null) {
                storedMedewerker.setGeboorteDatum(medewerkerUpdate.getGeboorteDatum());
            }
            medewerkers.set(medewerkers.indexOf(storedMedewerker), medewerkerUpdate);
            log.debug("Medewerker(id={}) updated:\n" + storedMedewerker);
        } else {
            throw new MedewerkerIsNotAvailableException();
        }
    }

    @POST
    @Path("/{id}/reserveer")
    public void reserveer(@PathParam("id") Long id, @QueryParam("datum") String reserveringsDatum) {
        double i = ThreadLocalRandom.current().nextDouble();
        if (i >= 0.15) {
            Medewerker storedMedewerker = find(id);
            storedMedewerker.setGereserveerdOp(LocalDate.parse(reserveringsDatum, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            log.debug("Medewerker(id={}) gereserveerd:\n" + storedMedewerker);
        } else {
            throw new MedewerkerIsNotAvailableException();
        }
    }

    @POST
    @Path("/{id}/vrijgeven")
    public void vrijgeven(@PathParam("id") Long id) {
        double i = ThreadLocalRandom.current().nextDouble();
        if (i >= 0.15) {
            Medewerker storedMedewerker = find(id);
            storedMedewerker.setGereserveerdOp(null);
            log.debug("Medewerker(id={}) vrijgegeven:\n" + storedMedewerker);
        } else {
            throw new MedewerkerIsNotAvailableException();
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        boolean deleted = medewerkers.removeIf(m -> m.getId().equals(id));

        if (deleted) {
            log.debug("Mederwerker(id={}) deleted", id);
        }
    }
}
