package com.submarine.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.submarine.resources.SubmarineEndpoint;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
		register(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
		register(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
		register(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
		register(org.glassfish.jersey.media.multipart.MultiPartFeature.class);

		registerResources();
	}

	private void registerResources() {
		// healthcheck
		register(com.submarine.healthcheck.HealthCheckResource.class);
		// endpoints
		register(SubmarineEndpoint.class);
	}
}
