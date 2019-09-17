package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.FormularForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class MainServlet is http servlet.
 * 
 * @author Filip
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<BlogUser> users = DAOProvider.getDAO().getListOfUsers();

		req.setAttribute("users", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Prijavi se".equals(metoda)) {
			req.getSession().removeAttribute("current.user.fn");
			req.getSession().removeAttribute("current.user.ln");
			req.getSession().removeAttribute("current.user.nick");
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		String nickName = req.getParameter("nick");
		BlogUser userWithNick = DAOProvider.getDAO().getUserByNick(nickName);

		if (userWithNick == null) {
			req.setAttribute("err", "Neispravno korisničko ime ili lozinka.");
		} else {
			String passw = req.getParameter("passw");
			String shaPassw = FormularForm.sha(passw);
			if (shaPassw.equals(userWithNick.getPasswordHash())) {
				req.getSession().setAttribute("current.user.fn", userWithNick.getFirstName());
				req.getSession().setAttribute("current.user.ln", userWithNick.getLastName());
				req.getSession().setAttribute("current.user.nick", userWithNick.getNick());
				resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
				return;
			} else {
				req.setAttribute("err", "Neispravno korisničko ime ili lozinka.");
			}
		}
		List<BlogUser> userss = DAOProvider.getDAO().getListOfUsers();

		req.setAttribute("users", userss);
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}

}
