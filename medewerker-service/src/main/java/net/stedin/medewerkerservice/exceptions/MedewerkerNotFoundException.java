package net.stedin.medewerkerservice.exceptions;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class MedewerkerNotFoundException extends WebApplicationException {
    public MedewerkerNotFoundException() {
        super(NOT_FOUND);
    }
}
