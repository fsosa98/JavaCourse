package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class SumWorker is implementation of IWebWorker.
 * 
 * @author Filip
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		int var1 = 1;
		int var2 = 2;

		String a = context.getParameter("a");
		String b = context.getParameter("b");

		if (a != null) {
			try {
				var1 = Integer.parseInt(a);
			} catch (NumberFormatException e) {
				var1 = 1;
			}

		}
		if (b != null) {
			try {
				var2 = Integer.parseInt(b);
			} catch (NumberFormatException e) {
				var2 = 2;
			}
		}
		context.setTemporaryParameter("varA", Integer.toString(var1));
		context.setTemporaryParameter("varB", Integer.toString(var2));
		context.setTemporaryParameter("zbroj", Integer.toString(var1 + var2));
		context.setTemporaryParameter("imgName", ((var1 + var2) % 2 == 0 ? "true" : "false") + ".png");
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
