package net.stedin.medewerkerservice.service;

import lombok.extern.slf4j.Slf4j;
import net.stedin.medewerkerservice.domain.Functie;
import net.stedin.medewerkerservice.domain.Medewerker;
import net.stedin.medewerkerservice.exceptions.MedewerkerNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/medewerkers")
public class MedewerkderService {
    static List<Medewerker> medewerkers = new ArrayList<>();
    
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
    
    @DELETE
    public void delete(Long id) {
        medewerkers.removeIf(m -> m.getId().equals(id));
    }
}
