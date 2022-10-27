package org.dieschnittstelle.ess.ser;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.dieschnittstelle.ess.utils.Utils.*;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;

public class TouchpointServiceServlet extends HttpServlet {

    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TouchpointServiceServlet.class);

    public TouchpointServiceServlet() {
        show("TouchpointServiceServlet: constructor invoked\n");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        logger.info("doGet()");

        // we assume here that GET will only be used to return the list of all
        // touchpoints

        // obtain the executor for reading out the touchpoints
        TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");
        try {
            // set the status
            response.setStatus(HttpServletResponse.SC_OK);
            // obtain the output stream from the response and write the list of
            // touchpoints into the stream
            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
            // write the object
            oos.writeObject(exec.readAllTouchpoints());
            oos.close();
        } catch (Exception e) {
            String err = "got exception: " + e;
            logger.error(err, e);
            throw new RuntimeException(e);
        }

    }

    /*
     * TODO: SER3 server-side implementation of createNewTouchpoint
     */
	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doPost()");

		try {
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			AbstractTouchpoint tp = (AbstractTouchpoint) ois.readObject();
			ois.close();

			TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");
			exec.createTouchpoint(tp);
			response.setStatus(HttpServletResponse.SC_OK);

			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(tp);
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

    /*
     * TODO: SER4 server-side implementation of deleteTouchpoint
     */

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        logger.info("doDelete()");

        TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");
        try {
            request.getRequestURL();
            String[] url_parts = request.getRequestURL().toString().split("/");
            long id = Integer.parseInt(url_parts[url_parts.length - 1]);
            exec.deleteTouchpoint(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            String err = "got exception: " + e;
            logger.error(err, e);
            throw new RuntimeException(e);
        }
    }

}
