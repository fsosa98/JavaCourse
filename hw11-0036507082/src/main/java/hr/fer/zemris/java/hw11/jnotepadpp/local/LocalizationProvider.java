package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class LocalizationProvider is localization provider. It is implementation of
 * AbstractLocalizationProvider.
 * 
 * @author Filip
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	private static final LocalizationProvider instance = new LocalizationProvider();
	private String language;
	private ResourceBundle bundle;

	/**
	 * Constructor
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
	}

	/**
	 * Getter for instance
	 * 
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Setter for language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
		fire();
	}

	/**
	 * Getter for language
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
