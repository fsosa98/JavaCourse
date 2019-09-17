package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class HomeWorker is implementation of IWebWorker.
 * 
 * @author Filip
 */
public class HomeWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		if (context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
