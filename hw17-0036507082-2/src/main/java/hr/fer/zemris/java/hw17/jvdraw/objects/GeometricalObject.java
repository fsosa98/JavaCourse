package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Class GeometricalObject is GeometricalObject.
 * 
 * @author Filip
 */
public abstract class GeometricalObject extends JPanel {

	private static final long serialVersionUID = 1L;
	List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Accept method
	 * 
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Create editor method
	 * 
	 * @return
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Listener add method
	 * 
	 * @param l
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
		fire();
	}

	/**
	 * Listener remove method
	 * 
	 * @param l
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
		fire();
	}

	/**
	 * Fire method
	 */
	public void fire() {
		for (GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}

}
