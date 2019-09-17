package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class LocalizableAction is implementation of AbstractAction.
 * 
 * @author Filip
 */
public abstract class LocalizableAction extends AbstractAction {

	private ILocalizationListener listener;
	private ILocalizationProvider prov;

	private String key;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param key
	 * @param prov
	 */
	public LocalizableAction(String key, ILocalizationProvider prov) {
		this.prov = prov;
		this.key = key;
		this.listener = this::update;
		update();
		prov.addLocalizationListener(listener);
	}

	/**
	 * Helper method for updating.
	 */
	private void update() {
		putValue(NAME, prov.getString(key));
		putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "desc"));
	}
}
