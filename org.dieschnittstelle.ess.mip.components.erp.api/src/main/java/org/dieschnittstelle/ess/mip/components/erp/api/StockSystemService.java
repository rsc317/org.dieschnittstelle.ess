package org.dieschnittstelle.ess.mip.components.erp.api;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

/**
 * TODO MIP3/4/6:
 * - declare the web api for this interface using JAX-RS
 * - implement the interface as a CDI Bean
 * - in the Bean implementation, delegate method invocations to the corresponding methods of the StockSystem Bean
 * - let the StockSystemClient in the client project access the web api via this interface - see ShoppingCartClient for an example
 */
@Path("/stocks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface StockSystemService {

	/**
	 * adds some units of a product to the stock of a point of sale
	 */
	@POST
	@Path("/add")
    void addToStock(
			@QueryParam("prodId") long productId,
			@QueryParam("posId") long pointOfSaleId,
			@QueryParam("units") int units
	);

	/**
	 * removes some units of a product from the stock of a point of sale
	 */
	@POST
	@Path(("/remove"))
    void removeFromStock(
			@QueryParam("prodId") long productId,
			@QueryParam("posId") long pointOfSaleId,
			@QueryParam("units") int units
	);

	/**
	 * returns all products on stock or, if pointOfSaleId is specified, the products for some pointOfSale
	 */
	@GET
	@Path("/products")
    List<IndividualisedProductItem> getProductsOnStock(@QueryParam("posId") long pointOfSaleId);

	/**
	 * returns the units on stock for a given product overall or, if a pointOfSaleId is specified, at some point of sale
	 */
	@GET
    int getUnitsOnStock(
			@QueryParam("prodId") long productId,
			@QueryParam("posId") long pointOfSaleId
	);

	/**
	 * returns the points of sale where some product is available
	 */
	@GET
	@Path("/pointsOfSale")
    List<Long> getPointsOfSale(@QueryParam("prodId") long productId);

}
