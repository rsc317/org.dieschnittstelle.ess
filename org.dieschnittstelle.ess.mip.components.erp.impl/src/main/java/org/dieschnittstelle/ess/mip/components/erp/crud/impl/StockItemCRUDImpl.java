package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.ProductAtPosPK;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static org.dieschnittstelle.ess.utils.Utils.show;

@ApplicationScoped
@Transactional
@Logged
public class StockItemCRUDImpl implements StockItemCRUD {

    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(PointOfSaleCRUDImpl.class);

    @Inject
    @EntityManagerProvider.ERPDataAccessor
    private EntityManager em;

    @Override
    public StockItem createStockItem(StockItem item) {
        logger.info("createStockItem(): " + item);

        em.persist(item);
        return item;
    }

    @Override
    public StockItem readStockItem(IndividualisedProductItem prod, PointOfSale pos) {
        //this doesnt work with OpenJPA
        //em.find(StockItem.class, new ProductAtPosPK(prod, pos));
        String queryString = "SELECT DISTINCT si FROM StockItem si WHERE si.product.id = " + prod.getId()
                + " AND si.pos.id = " + pos.getId();
        show("readStockItem(): queryString: " + queryString);

        Query query = em.createQuery(queryString);
        List<StockItem> sis = query.getResultList();

        if ( sis.size() == 0) {
            return null;
        }
        return sis.get(0);
    }

    @Override
    public StockItem updateStockItem(StockItem item) {
        return null;
    }

    @Override
    public List<StockItem> readStockItemsForProduct(IndividualisedProductItem prod) {
        return null;
    }

    @Override
    public List<StockItem> readStockItemsForPointOfSale(PointOfSale pos) {
        return null;
    }
}
