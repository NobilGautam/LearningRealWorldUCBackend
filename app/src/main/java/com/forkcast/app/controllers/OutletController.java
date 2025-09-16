package com.forkcast.app.controllers;

import com.forkcast.common.exceptions.OwnerNotFoundException;
import com.forkcast.common.exceptions.OutletNotFoundException;
import com.forkcast.dao.dto.OutletRequest;
import com.forkcast.dao.dto.OutletResponse;
import com.forkcast.service.OutletService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Path("/outlet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OutletController {

    @Autowired
    private OutletService outletService;

    @POST
    @Path("/register")
    public Response registerOutletToOwnerProfile(OutletRequest outletRequest) {
        try {
            OutletResponse response = outletService.registerOutlet(outletRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch (OwnerNotFoundException | OutletNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }

    @PUT
    @Path("/update")
    public Response updateOutletDetails(OutletRequest outletRequest) {
        try {
            OutletResponse response = outletService.updateOutlet(outletRequest);
            return Response.ok(response).build();
        } catch (OwnerNotFoundException | OutletNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }

    @DELETE
    @Path("/remove")
    public Response removeOutletFromOwnerProfile(OutletRequest outletRequest) {
        try {
            OutletResponse response = outletService.removeOutlet(outletRequest);
            return Response.ok(response).build();
        } catch (OutletNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }

    @GET
    @Path("/fetch")
    public Response fetchOutletDetailsForOwner(@QueryParam("email") String ownerEmail) {
        try {
            List<OutletResponse> response = outletService.getAllOutletsForOwner(ownerEmail);
            return Response.ok(response).build();
        } catch (OwnerNotFoundException | OutletNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }
}
