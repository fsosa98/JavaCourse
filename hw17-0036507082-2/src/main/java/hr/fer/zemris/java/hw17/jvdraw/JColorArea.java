package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Class JColorArea is color area class and implements provider.
 * 
 * @author Filip
 */
public class JColorArea extends JComponent implements IColorProvider {

	private Color selectedColor;
	private List<ColorChangeListener> listeners = new ArrayList<ColorChangeListener>();
	private Color oldColor;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param selectedColor2
	 */
	public JColorArea(Color selectedColor2) {
		this.selectedColor = selectedColor2;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JColorChooser jcc = new JColorChooser();
				Color col = jcc.showDialog(JColorArea.this, "Color", selectedColor);
				if (col != null) {
					oldColor = selectedColor;
					selectedColor = col;
					fire();
					repaint();
				}
			}

		});

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		g2d.fillRect(getInsets().left + 2, getInsets().top + 6, getPreferredSize().width, getPreferredSize().height);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Fire method
	 */
	private void fire() {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, selectedColor);
		}
	}

}
