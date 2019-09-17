package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class JPADAOImpl is jpa implementation of dao.
 * 
 * @author Filip
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getListOfUsers() throws DAOException {

		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser as b", BlogUser.class).getResultList();

		return users;
	}

	@Override
	public List<BlogEntry> getListOfBlogEntry(BlogUser blogUser) throws DAOException {
		List<BlogEntry> entries = JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogEntry as b where b.blogUser=:be", BlogEntry.class)
				.setParameter("be", blogUser).getResultList();
		return entries;
	}

	@Override
	public BlogUser getUserByNick(String nick) throws DAOException {
		List<BlogUser> users = getListOfUsers();
		for (BlogUser user : users) {
			if (user.getNick().equals(nick)) {
				return user;
			}
		}
		return null;
	}

}