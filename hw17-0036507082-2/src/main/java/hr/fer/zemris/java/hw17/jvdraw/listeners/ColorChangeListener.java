package hr.fer.zemris.java.hw17.jvdraw.listeners;

import java.awt.Color;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Interface ColorChangeListener is Color Change Listener.
 * 
 * @author Filip
 */
public interface ColorChangeListener {

	/**
	 * Listener for color change
	 * 
	 * @param source
	 * @param oldColor
	 * @param newColor
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
