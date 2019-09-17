package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Interface GeometricalObjectListener is Geometrical Object Listener.
 * 
 * @author Filip
 */
public interface GeometricalObjectListener {

	/**
	 * Listener for object change.
	 * 
	 * @param o
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
