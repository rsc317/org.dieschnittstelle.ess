package org.dieschnittstelle.ess.jrs.opi;

import java.util.List;
import java.util.stream.Collectors;

import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.jrs.IProductCRUDService;
import org.dieschnittstelle.ess.jrs.ProductCRUDServiceImpl;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("opi/products")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ProductCRUDServiceOPIImpl {

	private IProductCRUDService service;

	public ProductCRUDServiceOPIImpl() {

	}

	public ProductCRUDServiceOPIImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		this.service = new ProductCRUDServiceImpl(servletContext, request);
	}

    @POST
    @Path("/individualized_item")
	public IndividualisedProductItem createProduct(
			IndividualisedProductItem prod) {
		return (IndividualisedProductItem)this.service.createProduct(prod);
	}

	// TODO: ueberlegen Sie, wie Sie createCampaign() von createProduct() unterscheidbar machen koennen - wenn
	//  Sie JRS3 umgesetzt haben, koennen Sie die derzeit noch auskommentierte Codezeile aktivieren
    @POST
    @Path("/campaign")
	public Campaign createCampaign(
			Campaign prod) {
//		return (Campaign) this.service.createProduct(prod);
		return null;
	}

    @GET
    @Path("/individualized_item")
	public List<IndividualisedProductItem> readAllProducts() {
		return (List)this.service.readAllProducts()
				.stream()
				.filter(prod -> prod instanceof IndividualisedProductItem)
				.collect(Collectors.toList());
	}

    @PUT
    @Path("/individualized_item/{productsId}")
	public IndividualisedProductItem updateProduct(@PathParam("productsId") long id, IndividualisedProductItem update) {
		return (IndividualisedProductItem)this.service.updateProduct(id,update);
	}

    @DELETE
    @Path("/individualized_item")
	public boolean deleteProduct(long id) {
		return this.service.deleteProduct(id);
	}

    @GET
    @Path("/individualized_item")
	public IndividualisedProductItem readProduct(long id) {
		IndividualisedProductItem item = (IndividualisedProductItem)this.service.readProduct(id);
		return item;
	}
	
}
