package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/touchpoints")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ITouchpointCRUDService {

	@GET
	List<AbstractTouchpoint> readAllTouchpoints();

	@GET
	@Path("/{touchpointId}")
	AbstractTouchpoint readTouchpoint(@PathParam("touchpointId") long id);

	@POST
	AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint);

	@DELETE
	@Path("/{touchpointId}")
	boolean deleteTouchpoint(@PathParam("touchpointId") long id);

	@PUT
	@Path("/{touchpointId}")
	AbstractTouchpoint updateTouchpoint(@PathParam("touchpointId") long id, AbstractTouchpoint touchpoint);
}
