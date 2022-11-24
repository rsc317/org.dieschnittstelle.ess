package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IProductCRUDService {
	@POST
	AbstractProduct createProduct(AbstractProduct prod);
	@GET
	List<AbstractProduct> readAllProducts();
	@PUT
	@Path("/{productsId}")
	AbstractProduct updateProduct(@PathParam("productsId") long id, AbstractProduct update);
	@DELETE
	@Path("/{productsId}")
	boolean deleteProduct(@PathParam("productsId") long id);
	@GET
	@Path("/{productsId}")
	AbstractProduct readProduct(@PathParam("productsId") long id);
}
