package org.dieschnittstelle.ess.opi.client.junit;

// TODO: entfernen Sie die auskommentierten Codezeilen, nachdem Sie nach Umsetzung der server-seitigen
//  Entwicklunsmassnahmen fuer OPI1 erstmalig die client-seitigen Klassen fuer den Zugriff auf die WebAPI
//  generiert haben. Falls Ihre imports automatisch aktualisiert werden, dann entfernen Sie erst die Kommentare
//  aus der Implementierung der Klasse und kommentieren Sie die imports erst danach ein.

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.opi.client.api.DefaultApi;
import org.dieschnittstelle.ess.opi.client.entities.Campaign;
import org.dieschnittstelle.ess.opi.client.entities.IndividualisedProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductCRUDOpenAPIClient {

	private DefaultApi serviceProxy;

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDOpenAPIClient.class);

	public ProductCRUDOpenAPIClient() throws Exception {

		// TODO: OPI1: instantiieren Sie das serviceProxy Attribut unter Verwendung der generierten Klassen, und beruecksichtigen
		//  Sie, dass bei der JSON Verarbeitung unbekannte Attribute ignoriert werden sollen.
        serviceProxy = null;
	}

	public IndividualisedProductItem createProduct(IndividualisedProductItem prod) {
		IndividualisedProductItem created = serviceProxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

    public Campaign createCampaign(Campaign prod) {
        Campaign created = serviceProxy.createCampaign(prod);
        // as a side-effect we set the id of the created product on the argument before returning
        prod.setId(created.getId());
        return created;
    }


    public List<?> readAllProducts() {
		return serviceProxy.readAllProducts();
	}

	public IndividualisedProductItem updateProduct(IndividualisedProductItem update) {
		return serviceProxy.updateProduct(update.getId(),(IndividualisedProductItem)update);
	}

	public boolean deleteProduct(int id) {
		return serviceProxy.deleteProduct(id);
	}

	public IndividualisedProductItem readProduct(int id) {
		return serviceProxy.readProduct(id);
	}

}
