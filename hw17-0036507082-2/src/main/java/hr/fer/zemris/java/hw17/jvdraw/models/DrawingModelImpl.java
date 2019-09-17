package hr.fer.zemris.java.hw17.jvdraw.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Class DrawingModelImpl is Drawing Model implementation.
 * 
 * @author Filip
 */
public class DrawingModelImpl implements DrawingModel {

	private List<DrawingModelListener> listeners = new ArrayList<>();
	private List<GeometricalObject> geometricalObjects = new ArrayList<>();
	private boolean modified;

	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		geometricalObjects.add(object);
		int index = geometricalObjects.indexOf(object);
		fireAdd(index, index);
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = geometricalObjects.indexOf(object);
		geometricalObjects.remove(object);
		fireRemove(index, index);
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {

		int index0 = geometricalObjects.indexOf(object);
		int index1 = index0 + offset;

		if (index0 == -1 || index1 >= geometricalObjects.size())
			return;

		Collections.swap(geometricalObjects, index0, index1);
		fireChange(index0, index1);
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return geometricalObjects.indexOf(object);
	}

	@Override
	public void clear() {
		geometricalObjects.clear();
		modified = true;
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Fire method
	 * 
	 * @param index0
	 * @param index1
	 */
	public void fireAdd(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index0, index1);
		}
	}

	/**
	 * Fire method
	 * 
	 * @param index0
	 * @param index1
	 */
	public void fireRemove(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index0, index1);
		}
	}

	/**
	 * Fire method
	 * 
	 * @param index0
	 * @param index1
	 */
	public void fireChange(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index0, index1);
		}
	}

}
