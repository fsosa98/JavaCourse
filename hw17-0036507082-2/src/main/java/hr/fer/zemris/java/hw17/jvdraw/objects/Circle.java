package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Class Circle is implementation of GeometricalObject circle.
 * 
 * @author Filip
 */
public class Circle extends GeometricalObject {

	private static final long serialVersionUID = 1L;
	private Point s;
	private int r;
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param color
	 */
	public Circle(Color color) {
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param s
	 * @param r
	 * @param color
	 */
	public Circle(Point s, int r, Color color) {
		this.s = s;
		this.r = r;
		this.color = color;
	}

	/**
	 * Get point method
	 * 
	 * @return
	 */
	public Point getS() {
		return s;
	}

	/**
	 * Setter for point
	 * 
	 * @param s
	 */
	public void setS(Point s) {
		this.s = s;
	}

	/**
	 * Getter for radius.
	 * 
	 * @return
	 */
	public int getR() {
		return r;
	}

	/**
	 * Setter for radius
	 * 
	 * @param r
	 */
	public void setR(int r) {
		this.r = r;
	}

	/**
	 * Getter for color
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		// “Line (3,5)-(20,30)”,
		return "Circle (" + s.x + "," + s.y + "), " + r;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

}
