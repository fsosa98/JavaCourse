package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class ILocalizationProvider is localization provider
 * 
 * @author Filip
 */
public interface ILocalizationProvider {

	/**
	 * This method adds listener.
	 * 
	 * @param listener
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * This method removes listener.
	 * 
	 * @param listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Getter for string with given key.
	 * 
	 * @param key
	 * @return
	 */
	String getString(String key);

}
