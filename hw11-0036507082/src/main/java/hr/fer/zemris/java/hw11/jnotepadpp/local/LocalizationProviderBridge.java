package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class LocalizationProviderBridge is localization provider bridge. It is
 * implementation of AbstractLocalizationProvider.
 * 
 * @author Filip
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationListener listener;
	private ILocalizationProvider parent;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		listener = this::fire;
	}

	/**
	 * Helper connect method.
	 */
	public void connect() {
		if (!connected) {
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}

	/**
	 * Helper disconnect method.
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
		System.exit(0);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
