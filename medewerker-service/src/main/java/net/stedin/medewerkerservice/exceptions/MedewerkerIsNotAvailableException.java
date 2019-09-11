package net.stedin.medewerkerservice.exceptions;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class MedewerkerIsNotAvailableException extends WebApplicationException {

    public MedewerkerIsNotAvailableException() {
        super("Mederwerker not available", INTERNAL_SERVER_ERROR);
    }
}
