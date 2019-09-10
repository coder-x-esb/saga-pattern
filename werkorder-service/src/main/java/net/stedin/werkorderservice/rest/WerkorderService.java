package net.stedin.werkorderservice.rest;

import net.stedin.werkorderservice.domain.Werkorder;
import net.stedin.werkorderservice.exceptions.WerkorderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static net.stedin.werkorderservice.domain.WerkorderStatus.INACTIEF;

@Path("/werkorders")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class WerkorderService {

    static final Logger log = LoggerFactory.getLogger(WerkorderService.class);

    @GET
    public List<Werkorder> findAll() {
        return Werkorder.listAll();
    }

    @GET
    @Path("/{id}")
    public Werkorder find(@PathParam("id") Long id) {
        return (Werkorder) Optional.ofNullable(Werkorder.findById(id)).orElseThrow(() -> new WerkorderNotFoundException());
    }

    @POST
    @Transactional
    public void save(Werkorder wo) {
        wo.aanmaakDatum = LocalDate.now();
        wo.status = INACTIEF;
        Werkorder.persist(wo);
        log.debug("new wo added:\n" + wo);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Werkorder.delete("id = ?1", id);
    }
}
