package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class JPAEMFProvider is provider class for entity manager factory.
 * 
 * @author Filip
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;

	/**
	 * Getter for entity manager
	 * 
	 * @return
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for entity manager
	 * 
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}