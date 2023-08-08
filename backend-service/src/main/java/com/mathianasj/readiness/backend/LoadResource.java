package com.mathianasj.readiness.backend;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/load")
public class LoadResource {
    @Inject
    LoadService loadService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLoad() {
        Integer load = loadService.getLoad();
        return Response.ok(loadService.getLoad()).tag(Integer.toString(load)).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void setLoad(Integer load) {
        loadService.setLoad(load);
    }
}
