package org.dieschnittstelle.ess.wsv.client;

import java.lang.reflect.Proxy;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.Address;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.utils.Utils;
import org.dieschnittstelle.ess.wsv.client.service.ITouchpointCRUDService;

import org.dieschnittstelle.ess.wsv.interpreter.JAXRSClientInterpreter;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class AccessRESTServiceWithInterpreter {

    protected static Logger logger = org.apache.logging.log4j.LogManager
            .getLogger(AccessRESTServiceWithInterpreter.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

		/*
		 * TODO WSV1 (here and following TODOs): create an instance of the invocation handler passing the service
		 * interface and the base url
		 */
        JAXRSClientInterpreter invocationHandler = new JAXRSClientInterpreter(
                ITouchpointCRUDService.class,
                "http://localhost:8080/api"
        );

		/*
		 * TODO: create a client for the web service using Proxy.newProxyInstance()
		 */
        ITouchpointCRUDService serviceProxy = (ITouchpointCRUDService) Proxy.newProxyInstance(
                AccessRESTServiceWithInterpreter.class.getClassLoader(),
                new Class[]{ITouchpointCRUDService.class},
                invocationHandler
        );

        show("serviceProxy: " + serviceProxy);

        step();

        // 1) read out all touchpoints
        List<StationaryTouchpoint> tps = serviceProxy.readAllTouchpoints();
        show("read all: " + tps);


        // TODO: comment-in the call to delete() once this is handled by the invocation handler
		// 2) delete the touchpoint if there is one
		if (tps.size() > 0) {
          step();
			show("deleted: "
					+ serviceProxy.deleteTouchpoint(tps.get(0).getId()));
		}

		// 3) create a new touchpoint
        step();

        Address addr = new Address("Luxemburger Strasse", "10", "13353",
                "Berlin");
        StationaryTouchpoint tp = new StationaryTouchpoint(-1,
                "BHT WSV Verkaufsstand", addr);
        tp = (StationaryTouchpoint) serviceProxy.createTouchpoint(tp);
        show("created: " + tp);

        // this is for verifying that the touchpoint objects are created without data loss from the json data of the http response
        if (tp.getAddress() == null) {
            throw new RuntimeException("Something went wrong during touchpoint creation. The address of the touchpoint returned by createTouchpoint() is null.");
        }

        // TODO: comment-in the call to read() once this is handled
		/*
		 * 4) read out the new touchpoint
		 */
		show("read created: " + serviceProxy.readTouchpoint(tp.getId()));


        // TODO: comment-in the call to update() once this is handled
		/*
		 * 5) update the touchpoint
		 */
		// change the name
		step();
		tp.setName("BHT WSV Mensa");


		tp = serviceProxy.updateTouchpoint(tp.getId(), tp);
		show("updated: " + tp);

    }

    public static void step() {
        Utils.step();
    }
}

