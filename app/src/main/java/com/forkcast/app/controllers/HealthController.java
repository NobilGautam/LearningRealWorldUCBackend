package com.forkcast.app.controllers;

import com.forkcast.service.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Component
@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthController {

    @Autowired
    private HealthService healthService;

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    
    @GET
    public Response healthCheck() {
        log.debug("Health check endpoint called");
        
        ResponseEntity<String> healthResponse = healthService.checkHealth();
        
        return Response.ok(healthResponse).build();
    }
}
