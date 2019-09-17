package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class ColorServlet is http servlet. Sets background color if color parameter
 * is set.
 * 
 * @author Filip
 */
@WebServlet(name = "col", urlPatterns = { "/setcolor" })
public class ColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("color") != null) {
			req.getSession().setAttribute("pickedBgCol", req.getParameter("color"));
		}
		req.getRequestDispatcher("WEB-INF/pages/colors.jsp").forward(req, resp);
	}

}
