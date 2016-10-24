package com.playlogix.thinslice.resources;

import com.codahale.metrics.annotation.Timed;
import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.services.UserService;
import io.dropwizard.auth.Auth;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/02.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Timed
    @PermitAll
    @GET
    public User getUser(@Auth final User authUser, @NotNull @QueryParam("email") String email) {
        Optional<User> userOptional = this.userService.getUser(email);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new NotFoundException("User not found");
    }

    @Timed
    @PermitAll
    @POST
    public User postUser(@Auth final User authUser, @NotNull @Valid User user) {
        this.userService.postUser(user);
        return user;
    }
}
