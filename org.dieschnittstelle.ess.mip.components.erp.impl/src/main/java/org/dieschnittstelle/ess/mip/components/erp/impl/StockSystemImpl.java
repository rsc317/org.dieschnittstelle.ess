package org.dieschnittstelle.ess.mip.components.erp.impl;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.StockItemCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Transactional
@Logged
public class StockSystemImpl implements StockSystem {

    @Inject
    private StockItemCRUD stockItemCRUD;

    @Inject
    private PointOfSaleCRUD posCRUD;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem si = stockItemCRUD.readStockItem(product, pos);

        if (si == null) {
            si = new StockItem(product, pos, units);
            stockItemCRUD.createStockItem(si);
        } else {
            si.setUnits(si.getUnits() + units);
            stockItemCRUD.updateStockItem(si);
        }
    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        addToStock(product, pointOfSaleId, -units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        List<IndividualisedProductItem> products = new ArrayList<>();

        for ( StockItem si :stockItemCRUD.readStockItemsForPointOfSale(pos)){
            products.add(si.getProduct());
        }

        return products;
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        List<PointOfSale> posList = posCRUD.readAllPointsOfSale();
        HashMap<Long, IndividualisedProductItem> products = new HashMap<>();

        for(PointOfSale pos: posList) {
            for (StockItem si :stockItemCRUD.readStockItemsForPointOfSale(pos)){
                products.put(si.getProduct().getId(), si.getProduct());
            }
        }

        return new ArrayList<>(products.values());
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        return stockItemCRUD.readStockItem(product, pos).getUnits();
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        int totalUnits = 0;
        for (StockItem si : stockItemCRUD.readStockItemsForProduct(product)) {
            totalUnits += si.getUnits();
        }

        return totalUnits;
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        List<StockItem> siList = stockItemCRUD.readStockItemsForProduct(product);
        List<Long> posList = new ArrayList<>();

        for(StockItem si: siList) {
            posList.add(si.getPos().getId());
        }

        return posList;
    }
}
