package net.stedin.planningservice.exception;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class PlanningNotFoundException extends WebApplicationException {
    public PlanningNotFoundException() {
        super(NOT_FOUND);
    }
}
