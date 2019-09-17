package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class AuthorServlet is http servlet.
 * 
 * @author Filip
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] parts = req.getPathInfo().split("/");
		String nick = parts[1];

		BlogUser usercic = DAOProvider.getDAO().getUserByNick(nick);

		if (usercic == null) {
			req.setAttribute("messgError", "Korisnik ne postoji.");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
			return;
		}

		String curUser = (String) req.getSession().getAttribute("current.user.nick");
		boolean editable = false;
		if (usercic != null && curUser != null && curUser.equals(usercic.getNick())) {
			editable = true;
		}
		req.setAttribute("editable", editable);

		if (parts.length < 3) {

			List<BlogEntry> entries = DAOProvider.getDAO().getListOfBlogEntry(usercic);
			if (entries.isEmpty()) {
				entries = null;
			}
			req.setAttribute("entries", entries);

			req.setAttribute("userNick", nick);
			req.setAttribute("usercic", usercic);

			req.getRequestDispatcher("/WEB-INF/pages/Entries.jsp").forward(req, resp);
		} else if (parts.length == 3) {
			String option = parts[2];
			if (option.equals("new") || option.equals("edit")) {
				if (option.equals("edit")) {
					BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("idOfEntry")));
					req.setAttribute("titlee", entry.getTitle());
					req.setAttribute("textt", entry.getText());
					req.setAttribute("idEnt", Long.parseLong(req.getParameter("idOfEntry")));
				}
				req.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(req, resp);
			} else {
				try {
					long id = Long.parseLong(option);
					BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
					if (blogEntry == null) {
						req.setAttribute("messgError", "Dogodila se pogreška.");
						req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
						return;
					}
					req.setAttribute("entryID", id);
					req.setAttribute("entry", blogEntry);
					req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
				} catch (NumberFormatException exc) {
					req.setAttribute("messgError", "Dogodila se pogreška.");
					req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
					return;
				}
			}
		} else {
			req.setAttribute("messgError", "Greška");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String[] parts = req.getPathInfo().split("/");
		String nick = parts[1];

		String metoda = req.getParameter("metoda");

		if ("Odustani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
			return;
		}
		if ("Komentiraj".equals(metoda)) {
			String comment = req.getParameter("comm");
			String email = req.getParameter("email");
			BlogComment blogComment = new BlogComment();
			long idCur = 0;
			try {
				idCur = Long.parseLong(parts[2]);
			} catch (NumberFormatException exc) {
				req.setAttribute("messgError", "Dogodila se pogreška.");
				req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
				return;
			}
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(idCur);
			blogComment.setBlogEntry(blogEntry);
			if (email == null) {
				String nickName = (String) req.getSession().getAttribute("current.user.nick");
				email = DAOProvider.getDAO().getUserByNick(nickName).getEmail();
			}
			blogComment.setUsersEMail(email);
			blogComment.setMessage(comment);
			blogComment.setPostedOn(new Date(System.currentTimeMillis()));

			JPAEMProvider.getEntityManager().persist(blogComment);
			JPAEMProvider.close();

			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/" + idCur);
			return;
		}
		if ("Uredi blog".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/edit?idOfEntry="
					+ parts[2]);
			return;
		}

		String title = req.getParameter("title");
		String text = req.getParameter("text");

		if (title == null || text == null || title.isEmpty()) {
			req.setAttribute("err", "Unesite naslov i tekst.");
			req.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(req, resp);
			return;
		}

		BlogEntry entry = null;
		if (req.getParameter("idOfEntry")!=null) {
			entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("idOfEntry")));
		}
		
		if (entry == null) {
			entry = new BlogEntry();
			entry.setCreatedAt(new Date(System.currentTimeMillis()));
		}

		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(new Date(System.currentTimeMillis()));

		BlogUser usercic = DAOProvider.getDAO().getUserByNick(nick);
		entry.setBlogUser(usercic);

		JPAEMProvider.getEntityManager().persist(entry);
		JPAEMProvider.close();

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
	}

}
