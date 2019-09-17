package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Class LJMenu is implementation of JMenu.
 * 
 * @author Filip
 */
public class LJMenu extends JMenu {

	private ILocalizationListener listener;
	private ILocalizationProvider prov;
	private String key;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param key
	 * @param prov
	 */
	public LJMenu(String key, ILocalizationProvider prov) {
		this.listener = this::updateLabel;
		this.prov = prov;
		this.key = key;
		prov.addLocalizationListener(listener);
		updateLabel();
	}

	/**
	 * Helper method.
	 */
	private void updateLabel() {
		setText(prov.getString(key));
	}
}