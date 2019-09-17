package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class BgColorWorker is implementation of IWebWorker.
 * 
 * @author Filip
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		if (context.getParameter("bgcolor") != null) {
			String color = context.getParameter("bgcolor");
			if (checkColor(color)) {
				context.setPersistentParameter("bgcolor", color);
				context.write("<p>Color updated.</p><a href=/index2.html>Home</a>");
			} else {
				context.write("<p>Color updated.</p><a href=/index2.html>Home</a>");
			}
		}
	}

	/**
	 * Helper method for checking color.
	 * 
	 * @param color
	 * @return
	 */
	private boolean checkColor(String color) {
		char[] data = color.toUpperCase().toCharArray();
		if (data.length != 6)
			return false;
		for (char c : data) {
			if (c >= 'A' && c <= 'Z')
				continue;
			if (c >= '0' && c <= '9')
				continue;
			return false;
		}
		return true;
	}

}
