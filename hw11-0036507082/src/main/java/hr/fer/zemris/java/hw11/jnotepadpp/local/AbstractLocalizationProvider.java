package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AbstractLocalizationProvider is abstract localization provider
 * 
 * @author Filip
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	List<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fire method.
	 */
	public void fire() {
		listeners.forEach((l) -> l.localizationChanged());
	}

}
