package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.hw14.model.Polls;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class GlasanjeServlet is http servlet. Sets information about bands.
 * 
 * @author Filip
 */
@WebServlet(name = "glas", urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
		req.setAttribute("pollOptions", pollOptions);
		Polls pol = DAOProvider.getDao().getPollByID(Long.parseLong(pollID));
		req.setAttribute("poll", DAOProvider.getDao().getPollByID(Long.parseLong(pollID)));

		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
	}

}
