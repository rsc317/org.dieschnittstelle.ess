package org.dieschnittstelle.ess.jrs;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

public class ProductCRUDServiceImpl implements IProductCRUDService {
	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDServiceImpl.class);
	private final GenericCRUDExecutor<AbstractProduct> productCRUD;
	public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		this.productCRUD = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
		logger.debug("read out the touchpointCRUD from the servlet context: " + this.productCRUD);
	}

	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		return this.productCRUD.createObject(prod);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return productCRUD.readAllObjects();
	}

	@Override
	public AbstractProduct updateProduct(long id, AbstractProduct update) {
		return productCRUD.updateObject(update);
	}

	@Override
	public boolean deleteProduct(long id) {
		return productCRUD.deleteObject(id);
	}

	@Override
	public AbstractProduct readProduct(long id) {
		return productCRUD.readObject(id);
	}
}
