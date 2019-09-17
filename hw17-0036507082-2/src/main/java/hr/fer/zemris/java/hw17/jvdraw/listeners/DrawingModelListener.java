package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Interface DrawingModelListener is Drawing Model Listener.
 * 
 * @author Filip
 */
public interface DrawingModelListener {

	/**
	 * Listener method for adding
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Listener method for removing
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Listener method for changing
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
