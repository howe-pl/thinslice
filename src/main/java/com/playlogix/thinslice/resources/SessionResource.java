package com.playlogix.thinslice.resources;

import com.codahale.metrics.annotation.Timed;
import com.playlogix.thinslice.api.Session;
import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.services.SessionService;
import io.dropwizard.auth.Auth;
import org.bson.types.ObjectId;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private SessionService sessionService;

    public SessionResource(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Timed
    @POST
    public Session postSession(@NotNull @Valid final Session session) {
        Optional<Session> sessionOptional = this.sessionService.createSession(session);
        if(sessionOptional.isPresent()) {
            return sessionOptional.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Timed
    @PermitAll
    @DELETE
    public Response deleteSession(@Auth final User authUser, @NotNull @QueryParam("id") String sessionId) {
//        if (session.getId() == null || !ObjectId.isValid(session.getId().toString())) {
//            throw new BadRequestException("Session.id is null or invalid");
//        }
        boolean success = this.sessionService.deleteSession(new ObjectId(sessionId));
        if(success) {
            return Response.status(Response.Status.OK).build();
        } else {
            throw new NotFoundException("Session not found");
        }
    }
}
