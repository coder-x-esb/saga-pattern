package net.stedin.planningservice.exception;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class PlanningException extends WebApplicationException {
    public PlanningException(String msg) {
        super(msg, INTERNAL_SERVER_ERROR);
    }
}
