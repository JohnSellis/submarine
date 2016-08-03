package com.submarine.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.submarine.domain.Position;
import com.submarine.domain.Submarine;
import com.submarine.exception.InvalidSubmarineActionsException;
import com.submarine.exception.InvalidSubmarineNameException;
import com.submarine.exception.SubmarineNotFoundException;
import com.submarine.services.SubmarineService;
import com.submarine.sharedvos.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Component
@Path("/")
@Api(value = "Submarine Endpoints", description = "The endpoints of the submarine app")
public class SubmarineEndpoint {

	private static final Logger log = LoggerFactory.getLogger(SubmarineEndpoint.class);

	@Autowired
	private SubmarineService submarineService;

	@Path("/submarines")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "GET", notes = "List the submarines.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "An array of submarines"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response readSubmarines() {
		try {
			List<Submarine> submarines = submarineService.readSubmarine();
			return Response.status(Response.Status.OK).entity(submarines).build();
		} catch (Exception e) {
			log.debug("Error processing script: {}", e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@GET
	@Path("/submarines/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "GET", notes = "Returns information about the submarine with the given id.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A submarine"),
			@ApiResponse(code = 404, message = "Submarine with this id not found"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response readSubmarines(@PathParam("id") Long id) {
		try {
			Submarine submarine = submarineService.readSubmarine(id);
			return Response.status(Response.Status.OK).entity(submarine).build();
		} catch (SubmarineNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Submarine with id " + id + " not found")).build();
		} catch (Exception e) {
			log.debug("Error getSettings({}): {}", id, e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@PUT
	@Path("/submarines")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "PUT", notes = "Creates a new Submarine.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The submarine created"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response createSubmarine(@QueryParam("name") @DefaultValue("unnamed") String name) {
		try {
			Submarine submarine = submarineService.createSubmarine(name);
			return Response.status(Response.Status.OK).entity(submarine).build();
		} catch (Exception e) {
			log.debug("Error getSettings: {}", e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@POST
	@Path("/submarines/{id}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "POST", notes = "Update a Submarine.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The submarine updated"),
			@ApiResponse(code = 400, message = "Invalid id or name"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response updateSubmarine(@PathParam("id") Long id, @PathParam("name") String name) {
		try {
			Submarine submarine = submarineService.updateSubmarine(id, name);
			return Response.status(Response.Status.OK).entity(submarine).build();
		} catch (SubmarineNotFoundException | InvalidSubmarineNameException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new Message(e.getMessage())).build();
		} catch (Exception e) {
			log.debug("Error getSettings: {}", e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@DELETE
	@Path("/submarines/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "DELETE", notes = "Remove the submarine with the given id.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Submarine with this id not found"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response deleteSubmarines(@PathParam("id") Long id) {
		try {
			submarineService.deleteSubmarine(id);
			return Response.status(Response.Status.OK).build();
		} catch (SubmarineNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Submarine with id " + id + " not found")).build();
		} catch (Exception e) {
			log.debug("Error getSettings({}): {}", id, e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@PUT
	@Path("/submarines/{id}/do/{actions}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "PUT", notes = "Perform actions, like 'LMRDDMMUU' or 'RMMLMMMDDLL', "
			+ "in the submarine with the given id.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The Submarine with the last Position"),
			@ApiResponse(code = 404, message = "Submarine with this id not found, or invalid actions"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response doActions(@PathParam("id") Long id, @PathParam("actions") String actions) {
		try {
			Submarine submarine = submarineService.performActions(id, actions);
			return Response.status(Response.Status.OK).entity(submarine).build();
		} catch (SubmarineNotFoundException | InvalidSubmarineActionsException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(new Message(e.getMessage())).build();
		} catch (Exception e) {
			log.debug("Error getSettings({}): {}", id, e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@GET
	@Path("/submarines/{id}/last")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "GET", notes = "Returns information about the last position of the submarine with the given id.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A submarine's last position"),
			@ApiResponse(code = 404, message = "Submarine with this id not found"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response lastPosition(@PathParam("id") Long id) {
		try {
			Position position = submarineService.readSubmarine(id).getLastPosition();
			return Response.status(Response.Status.OK).entity(position).build();
		} catch (SubmarineNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(new Message(e.getMessage())).build();
		} catch (Exception e) {
			log.debug("Error getSettings({}): {}", id, e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

	@GET
	@Path("/submarines/{id}/route")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "GET", notes = "Returns the navigated route of the submarine with the given id.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A submarine's route navigation"),
			@ApiResponse(code = 404, message = "Submarine with this id not found"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public Response route(@PathParam("id") Long id) {
		try {
			List<Position> route = submarineService.readSubmarine(id).getRoute();
			return Response.status(Response.Status.OK).entity(route).build();
		} catch (SubmarineNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(new Message(e.getMessage())).build();
		} catch (Exception e) {
			log.debug("Error getSettings({}): {}", id, e.getMessage());
			log.info(e.getStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Unexpected error"))
					.build();
		}
	}

}
