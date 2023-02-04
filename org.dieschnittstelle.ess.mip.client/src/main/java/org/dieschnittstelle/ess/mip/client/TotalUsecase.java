package org.dieschnittstelle.ess.mip.client;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.Customer;
import org.dieschnittstelle.ess.mip.client.shopping.ShoppingBusinessDelegate;
import org.dieschnittstelle.ess.mip.client.shopping.ShoppingSession;
import org.dieschnittstelle.ess.mip.client.shopping.PurchaseServiceClient;
import org.dieschnittstelle.ess.mip.components.crm.api.CampaignTracking;
import org.dieschnittstelle.ess.mip.components.crm.api.CrmException;
import org.dieschnittstelle.ess.mip.components.crm.api.CustomerTracking;
import org.dieschnittstelle.ess.mip.components.crm.api.TouchpointAccess;
import org.dieschnittstelle.ess.mip.components.crm.crud.api.CustomerCRUD;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.entities.crm.CampaignExecution;
import org.dieschnittstelle.ess.entities.crm.CustomerTransaction;
import org.dieschnittstelle.ess.mip.client.apiclients.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.dieschnittstelle.ess.mip.client.Constants.*;
import static org.dieschnittstelle.ess.utils.Utils.step;

public class TotalUsecase {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TotalUsecase.class);

	public static void main(String[] args) {
		ServiceProxyFactory.initialise();

		try {
			(new TotalUsecase()).runAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this is not used currently
	private boolean async = false;
	
	// declare the session as stepping or not
	private boolean stepping = true;
	// allow to switch error provocation from outside via setting this attribute
	// TODO: ADD4: set to true for testing ShoppingException, set to false for testing success-case for transactions
	private boolean provokeErrorOnPurchase = false /*true*/;

	// TODO: PAT1: set to true for testing purchase service
	// TODO: ADD4: set to true for testing success-case for transactions and ShoppingException
	private boolean usePurchaseServiceClient = true;

	// declare the attributes that will be instantiated with the service clients
	private ProductCRUD productCRUD;
	private TouchpointAccess touchpointAccess;
	private StockSystem stockSystem;
	private CustomerCRUD customerCRUD;
	private CampaignTracking campaignTracking;
	private CustomerTracking customerTracking;

	public TotalUsecase() throws Exception {
		instantiateClients();
	}
	
	public void runAll() {

		System.out.println("\n%%%%%%%%%%%% TotalUsecase: " + (this.provokeErrorOnPurchase ? "ShoppingException will be provoked (ADD4)" : "will run regularly") + ", using " + (true ? "WebAPI clients" :"EJB clients") + " for accessing server-side components; " + (this.usePurchaseServiceClient ? "remote purchase service will be used (PAT)" : "will use local ShoppingSession implementation") + " %%%%%%%%%%%\n\n");

		if (this.stepping) step();

		try {
			createProducts();
			createTouchpoints();

			createStock(this.provokeErrorOnPurchase);

			prepareCampaigns();
			createCustomers();

			doShopping();

			showTransactions();
		} catch (Exception e) {
			logger.error("got exception: " + e, e);
		}
	}

	public void instantiateClients() throws Exception {
		// instantiate the clients
		productCRUD = new ProductCRUDClient();
		touchpointAccess = new TouchpointAccessClient();
		stockSystem = new StockSystemClient();
		customerCRUD = new CustomerCRUDClient();
		campaignTracking = new CampaignTrackingClient();
		customerTracking = new CustomerTrackingClient();

		System.out.println("\n***************** instantiated clients\n");
	}

	public void createProducts() {
		// create products
		productCRUD.createProduct(PRODUCT_1);
		productCRUD.createProduct(PRODUCT_2);
		productCRUD.createProduct(PRODUCT_3);
		productCRUD.createProduct(CAMPAIGN_1);
		productCRUD.createProduct(CAMPAIGN_2);

		System.out.println("\n***************** created products\n");
	}

	public void createTouchpoints() {
		// create touchpoints
		try {
			touchpointAccess.createTouchpointAndPointOfSale(TOUCHPOINT_1);
			touchpointAccess.createTouchpointAndPointOfSale(TOUCHPOINT_2);

			System.out.println("\n***************** created touchpoints\n");
		}
		catch (CrmException e) {
			throw new RuntimeException("createTouchpoints(): got exception " + e,e);
		}
	}

	// in order to verify the usage of shopping exception in ADD4 this method can be called with provokeError
	// being set to true
	public void createStock(boolean provokeError) {
		int units = provokeError ? 5 : 100;

		// create stock
		stockSystem.addToStock(PRODUCT_1,
				TOUCHPOINT_1.getErpPointOfSaleId(), units);
		stockSystem.addToStock(PRODUCT_1,
				TOUCHPOINT_2.getErpPointOfSaleId(), units);
		stockSystem.addToStock(PRODUCT_2,
				TOUCHPOINT_1.getErpPointOfSaleId(), units);
		stockSystem.addToStock(PRODUCT_2,
				TOUCHPOINT_2.getErpPointOfSaleId(), units);
		stockSystem.addToStock(PRODUCT_3,
				TOUCHPOINT_1.getErpPointOfSaleId(), units);
		stockSystem.addToStock(PRODUCT_3,
				TOUCHPOINT_2.getErpPointOfSaleId(), units);

		System.out.println("\n***************** created stock\n");
	}

	public void prepareCampaigns() {
		// create campaign executions
		campaignTracking.addCampaignExecution(new CampaignExecution(
				Constants.TOUCHPOINT_1, Constants.CAMPAIGN_1.getId(), 10, -1));
		campaignTracking.addCampaignExecution(new CampaignExecution(
				Constants.TOUCHPOINT_1, Constants.CAMPAIGN_2.getId(), 5, -1));

		logger.info("campaigns are: "
				+ campaignTracking.getAllCampaignExecutions());

		System.out.println("\n***************** created campaign executions\n");
	}

	public void createCustomers() {
		// create customers
		customerCRUD.createCustomer(CUSTOMER_1);
		customerCRUD.createCustomer(CUSTOMER_2);

		System.out.println("\n***************** created customers\n");
	}

	public void doShopping() {
		try {
			int shoppingcount = 0;
			while (true) {
				try {
					// create a shopping session and initialise it such that
					// it can access the required beans
					ShoppingBusinessDelegate session;

					if (!usePurchaseServiceClient) {
						session = new ShoppingSession();
					}
					else {
						// for PAT1: use the ShoppingSessionClient as implementation of the business delegate
						session = new PurchaseServiceClient();
					}
					
					// add a customer and a touchpoint
					session.setCustomer(Constants.CUSTOMER_1);
					session.setTouchpoint(Constants.TOUCHPOINT_1);

					// now add items
					session.addProduct(Constants.PRODUCT_1, 2);
					session.addProduct(Constants.PRODUCT_1, 3);
					session.addProduct(Constants.PRODUCT_2, 2);
					session.addProduct(Constants.CAMPAIGN_1, 1);
					session.addProduct(Constants.CAMPAIGN_2, 2);

					System.out.println("\nWill finalise " + ++shoppingcount + "st/nd/rd shopping transaction...");
					if (this.stepping) step();

					// now try to commit the session
					session.purchase();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					// throwing exceptions out of main is bad style, yet we
					// need it to interrupt shopping in TotalUsecase
					throw new RuntimeException(e);
				}
				if (this.stepping) step();
			}
		} catch (Exception e) {
			logger.error("got exception during shopping: " + e, e);
			System.out.println("\nNote: if the previous step was the third attempt to finalise a shopping transaction, the exception 'verifyCampaigns() failed' is intended and indicates that the system is working properly.");
			if (this.stepping) step();
		}
	}

	public void showTransactions() {
		System.out.println("\n***************** show transactions\n");

		Collection<CustomerTransaction> trans = new ArrayList<>();

		// in the total usecase scenario, all three methods for transaction access return the same
		// list of transactions as a single customer makes three purchases at a single touchpoint
//		trans = customerTracking
//				.readTransactions(Constants.TOUCHPOINT_1.getId(),0);
//		logger.info("transactions for touchpoint are: " + trans);
//		trans = customerTracking
//				.readTransactions(0, Constants.CUSTOMER_1.getId());
//		logger.info("transactions for customer are: " + trans);
		trans = customerTracking.
				readTransactions(TOUCHPOINT_1.getId(), Constants.CUSTOMER_1.getId());
		logger.info("transactions for touchpoint and customer are: " + trans);

		// we finally show transactions for some selected product
		trans = customerTracking.readTransactionsForProduct(CAMPAIGN_1.getId());
		logger.info("transactions for campaign with id " + CAMPAIGN_1.getId() + " are: " + trans);

		List<Customer> customers = customerTracking.readAllCustomersForProduct(CAMPAIGN_1.getId());
		logger.info("customers for campaign with id " + CAMPAIGN_1.getId() + " are: " + customers
				.stream()
				.map(c -> c.getFirstName() + " " + c.getLastName())
				// as long as the ProductCRUD has not been implemented we will obtain
				// name duplicates from the jpql query. Avoid irritation by forcing
				// distinct customer names
				.distinct()
				.collect(Collectors.joining(", ")));

	}

	public void setStepping(boolean stepping) {
		this.stepping = stepping;
	}

	public void setProvokeErrorOnPurchase(boolean provoke) {
		this.provokeErrorOnPurchase = provoke;
	}

	public void setUsePurchaseServiceClient(boolean use) {
		this.usePurchaseServiceClient = use;
	}


}
