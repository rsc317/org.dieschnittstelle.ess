package org.dieschnittstelle.ess.mip.components.erp.crud.api;

import java.util.List;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/*
 * TODO MIP+JPA1/2/5:
 * this interface shall be implemented using an ApplicationScoped CDI bean with an EntityManager.
 * See TouchpointCRUDImpl for an example bean with a similar scope of functionality
 */

//@RegisterRestClient
@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface ProductCRUD {

	@POST
	public AbstractProduct createProduct(AbstractProduct prod);
	@GET
	public List<AbstractProduct> readAllProducts();
	@PUT
	public AbstractProduct updateProduct(AbstractProduct update);
	@GET
	@Path("/{productId}")
	public AbstractProduct readProduct(@PathParam("productId") long productID);
	@DELETE
	@Path("/{productId}")
	public boolean deleteProduct(@PathParam("productId") long productID);
	@GET
	@Path("/campaigns")
	public List<Campaign> getCampaignsForProduct(@QueryParam("productId") long productID);

}
