package com.playlogix.thinslice.resources;

import com.codahale.metrics.annotation.Timed;
import com.playlogix.thinslice.api.Activity;
import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.services.ActivityService;
import io.dropwizard.auth.Auth;
import org.bson.types.ObjectId;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Stephen on 2016/08/23.
 */
@Path("user/{userId}/activity")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    private ActivityService activityService;

    public ActivityResource(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Timed
    @PermitAll
    @GET
    public ArrayList<Activity> getActivities(@Auth final User authUser, @NotNull @PathParam("userId") String userId) {
        ArrayList<Activity> activities = this.activityService.getActivities(new ObjectId(userId));
        return activities;
    }

    @Timed
    @PermitAll
    @POST
    public Activity postActivity(@Auth final User authUser, @NotNull @Valid Activity activity) {
        this.activityService.saveActivity(activity);
        return activity;
    }

    @Timed
    @PermitAll
    @DELETE
    public Response deleteSession(@Auth final User authUser, @NotNull @PathParam("userId") String sessionId) {
        boolean success = this.activityService.deleteActivity(new ObjectId(sessionId));
        if(success) {
            return Response.status(Response.Status.OK).build();
        } else {
            throw new NotFoundException("Activity not found");
        }
    }
}
