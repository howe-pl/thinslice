package com.playlogix.thinslice.resources;

import com.playlogix.thinslice.api.Test;
import com.playlogix.thinslice.services.TestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Stephen on 2016/10/21.
 */
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
    TestService testService;

    public TestResource(TestService testService) {
        this.testService = testService;
    }

    @GET
    public List<Test> test() {
        return this.testService.test();
    }
}
