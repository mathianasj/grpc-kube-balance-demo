package com.mathianasj.readiness.backend;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Readiness
@ApplicationScoped
public class LoadReadiness implements HealthCheck {
    @Inject
    LoadService loadService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("load");

        if(loadService.getLoad() < 5) {
            responseBuilder.up();
        }

        return responseBuilder.build();
    }
    
}
