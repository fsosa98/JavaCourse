package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class EchoParams is implementation of IWebWorker.
 * 
 * @author Filip
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n" + "<head>\n" + "<style>\n" + "table, th, td {\n" + "  border: 1px solid black;\n" + "}"
				+ "tr:hover {background-color: #f5f5f5;}" + "</style>" + "</head>\n" + "<body>\n" + "<table>\n"
				+ "<tr>\n" + "    <th>Name</th>\n" + "    <th>Value</th>\n" + "  </tr>");
		for (String par : context.getParameterNames()) {
			sb.append("  <tr>\n" + "    <td>" + " " + par + " " + "</td>\n" + "    <td> " + context.getParameter(par)
					+ " </td>\n" + "  </tr>\n");
		}
		sb.append("</table>\n" + "\n" + "</body>\n" + "</html>");
		context.write(new String(sb));
	}

}
