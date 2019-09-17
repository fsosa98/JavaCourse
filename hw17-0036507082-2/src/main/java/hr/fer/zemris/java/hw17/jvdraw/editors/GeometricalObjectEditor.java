package hr.fer.zemris.java.hw17.jvdraw.editors;

import javax.swing.JPanel;

/**
 * Class GeometricalObjectEditor is object editor class extends panel.
 * 
 * @author Filip
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Checking method
	 */
	public abstract void checkEditing();

	/**
	 * Method for accepting.
	 */
	public abstract void acceptEditing();

}
