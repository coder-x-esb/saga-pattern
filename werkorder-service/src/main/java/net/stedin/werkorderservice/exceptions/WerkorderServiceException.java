package net.stedin.werkorderservice.exceptions;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class WerkorderServiceException extends WebApplicationException {
    public WerkorderServiceException(String msg) {
        super(msg, INTERNAL_SERVER_ERROR);
    }
}
