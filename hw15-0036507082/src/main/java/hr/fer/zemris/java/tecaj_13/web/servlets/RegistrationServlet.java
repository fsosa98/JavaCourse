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
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class RegistrationServlet is http servlet.
 * 
 * @author Filip
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BlogUser r = new BlogUser();
		FormularForm f = new FormularForm();
		f.fillFromBlogUser(r);

		req.setAttribute("form", f);

		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		FormularForm f = new FormularForm();
		f.fillFromHttpRequesta(req);
		f.validate();

		if (f.containsError()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
			return;
		}

		BlogUser r = new BlogUser();
		f.fillInBlogUser(r);

		List<BlogUser> users = DAOProvider.getDAO().getListOfUsers();

		for (BlogUser user : users) {
			if (user.getNick().equals(r.getNick())) {
				f.setError("nick", "Nadimak već postoji.");
				req.setAttribute("form", f);
				req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
				return;
			}
		}
		JPAEMProvider.getEntityManager().persist(r);
		JPAEMProvider.close();

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
