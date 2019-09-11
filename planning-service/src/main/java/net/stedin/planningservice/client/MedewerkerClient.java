package net.stedin.planningservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient
@Path("/medewerkers")
public interface MedewerkerClient {

    @GET
    @Path("/{id}")
    JsonNode find(@PathParam("id") Long id);
}
