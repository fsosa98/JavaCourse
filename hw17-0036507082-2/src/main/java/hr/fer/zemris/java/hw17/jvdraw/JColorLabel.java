package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Class JColorLabel is label class and implements ColorChangeListener.
 * 
 * @author Filip
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 1L;
	private IColorProvider provider1;
	private IColorProvider provider2;
	private String teext;

	/**
	 * Constructor
	 * 
	 * @param provider1
	 * @param provider2
	 */
	public JColorLabel(IColorProvider provider1, IColorProvider provider2) {
		this.provider1 = provider1;
		this.provider2 = provider2;
		provider1.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				updateText();
			}
		});

		provider2.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				updateText();
			}
		});
		updateText();
	}

	/**
	 * Getter for teext
	 * 
	 * @return
	 */
	public String getTeext() {
		return teext;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Helper method for update text.
	 */
	private void updateText() {
		teext = "Foreground color: (" + provider1.getCurrentColor().getRed() + ", "
				+ provider1.getCurrentColor().getGreen() + ", " + provider1.getCurrentColor().getBlue()
				+ "), background color: (" + provider2.getCurrentColor().getRed() + ", "
				+ provider2.getCurrentColor().getGreen() + ", " + provider2.getCurrentColor().getBlue() + ")";
	}

}
