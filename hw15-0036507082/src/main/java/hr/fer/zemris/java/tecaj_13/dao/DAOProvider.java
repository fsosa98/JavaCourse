package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Class DAOProvider is provider class. Useres can get dao.
 * 
 * @author Filip
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter for dao
	 * 
	 * @return
	 */
	public static DAO getDAO() {
		return dao;
	}

}