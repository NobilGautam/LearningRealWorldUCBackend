package com.forkcast.app.config;

import com.forkcast.app.controllers.AuthController;
import com.forkcast.app.controllers.HealthController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import jakarta.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    
    public JerseyConfig() {
        // Register controllers
        register(AuthController.class);
        register(HealthController.class);
        
        // Register features
        packages("com.forkcast.app.controllers");
    }
}
