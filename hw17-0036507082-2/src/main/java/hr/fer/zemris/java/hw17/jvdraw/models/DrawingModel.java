package hr.fer.zemris.java.hw17.jvdraw.models;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Interface DrawingModel is Drawing Model.
 * 
 * @author Filip
 */
public interface DrawingModel {

	/**
	 * Getter for size
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Getter for object
	 * 
	 * @param index
	 * @return
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adding method
	 * 
	 * @param object
	 */
	public void add(GeometricalObject object);

	/**
	 * Removing method
	 * 
	 * @param object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Change order method
	 * 
	 * @param object
	 * @param offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Index of method
	 * 
	 * @param object
	 * @return
	 */
	public int indexOf(GeometricalObject object);

	public void clear();

	public void clearModifiedFlag();

	public boolean isModified();

	public void addDrawingModelListener(DrawingModelListener l);

	public void removeDrawingModelListener(DrawingModelListener l);
}
