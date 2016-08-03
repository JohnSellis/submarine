package com.submarine.healthcheck;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.submarine.sharedvos.EnvelopeMetaHttpStatus;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("healthcheck")
@Api(value = "HealthCheck", description = "Asks HealthCheck to evaluates - version 1")
public class HealthCheckResource {

	@GET
	@ApiOperation(value = "GET", notes = "Get Lite Health Check")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.TEXT_PLAIN)
	public Response lite() {
		return Response.status(EnvelopeMetaHttpStatus.HTTP_200.CODE).entity("LIVE").build();
	}

}
