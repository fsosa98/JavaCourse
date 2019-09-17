package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Returns list of all users.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<BlogUser> getListOfUsers() throws DAOException;

	/**
	 * Returns list of blogEntries from given user.
	 * 
	 * @param blogUser
	 * @return
	 * @throws DAOException
	 */
	public List<BlogEntry> getListOfBlogEntry(BlogUser blogUser) throws DAOException;

	/**
	 * Returns user with given nickname.
	 * 
	 * @param nick
	 * @return
	 * @throws DAOException
	 */
	public BlogUser getUserByNick(String nick) throws DAOException;

}