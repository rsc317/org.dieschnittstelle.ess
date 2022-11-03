package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IProductCRUDService {
	@POST
	IndividualisedProductItem createProduct(IndividualisedProductItem prod);
	@GET
	List<IndividualisedProductItem> readAllProducts();
	@PUT
	@Path("/{productsId}")
	IndividualisedProductItem updateProduct(@PathParam("productsId") long id, IndividualisedProductItem update);
	@DELETE
	@Path("/{productsId}")
	boolean deleteProduct(@PathParam("productsId") long id);
	@GET
	@Path("/{productsId}")
	IndividualisedProductItem readProduct(@PathParam("productsId") long id);
}
