package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@Logged
@Transactional
public class ProductCRUDImpl implements ProductCRUD {
    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDImpl.class);

    @Inject
    @EntityManagerProvider.ERPDataAccessor
    private EntityManager em;
    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        em.persist(prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        Query query = em.createQuery("SELECT p from AbstractProduct p");
        return query.getResultList();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return null;
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return null;
    }

    @Override
    public boolean deleteProduct(long productID) {
        return false;
    }

    @Override
    public List<Campaign> getCampaignsForProduct(long productID) {
        return null;
    }
}
