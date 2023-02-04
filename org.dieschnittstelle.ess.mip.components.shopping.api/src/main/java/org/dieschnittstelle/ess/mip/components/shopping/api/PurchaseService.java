package org.dieschnittstelle.ess.mip.components.shopping.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// TODO: PAT1: this is the interface to be provided as a rest service if rest service access is used
@Path("/purchase")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PurchaseService {

	@POST
	void purchaseCartAtTouchpointForCustomer(@QueryParam("cartId") long shoppingCartId, @QueryParam("tpId") long touchpointId, @QueryParam("custId") long customerId) throws ShoppingException;
	
}
