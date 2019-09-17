package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface Tool is tool interface.
 * 
 * @author Filip
 */
public interface Tool {

	/**
	 * Method on mouse pressed event
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Method on mouse released event
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Method on mouse clicked event
	 * 
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Method on mouse moved event
	 * 
	 * @param e
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Method on mouse dragged event
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method for paint
	 * 
	 * @param e
	 */
	public void paint(Graphics2D g2d);

}
