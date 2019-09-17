package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.Polls;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class IndexIndexServlet is http servlet. Redirects to index servlet.
 * 
 * @author Filip
 */
@WebServlet(name = "ind", urlPatterns = { "/", "/index.html" })
public class IndexIndexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}

}
