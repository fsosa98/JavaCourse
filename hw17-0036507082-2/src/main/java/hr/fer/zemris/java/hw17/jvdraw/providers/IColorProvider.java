package hr.fer.zemris.java.hw17.jvdraw.providers;

import java.awt.Color;
import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Interface IColorProvider is color provider.
 * 
 * @author Filip
 */
public interface IColorProvider {

	/**
	 * Getter for color
	 * 
	 * @return
	 */
	public Color getCurrentColor();

	/**
	 * Method for adding listeners
	 * 
	 * @param l
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Method for removing listeners
	 * 
	 * @param l
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
