package net.stedin.werkorderservice.rest;

import lombok.extern.slf4j.Slf4j;
import net.stedin.werkorderservice.domain.Werkorder;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDate;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static net.stedin.werkorderservice.domain.WerkorderStatus.INACTIEF;

@Slf4j
@Path("/werkorders")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class WerkorderService {

    @GET
    public List<Werkorder> findAll() {
        return Werkorder.listAll();
    }

    @GET
    @Path("/{id}")
    public Werkorder find(@PathParam("id") Long id) {
        return Werkorder.findById(id);
    }

    @POST
    public void save(Werkorder wo) {
        wo.setAanmaakDatum(LocalDate.now());
        wo.setStatus(INACTIEF);
        Werkorder.persist(wo);
        this.log.debug("new wo added:\n" + wo);
    }

    @DELETE
    public void delete(Long id) {
        Werkorder.delete("id = ?1", id);
    }
}
