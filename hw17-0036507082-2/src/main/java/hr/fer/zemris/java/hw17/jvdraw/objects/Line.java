package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Class Line is GeometricalObject implementation of line.
 * 
 * @author Filip
 */
public class Line extends GeometricalObject {

	private static final long serialVersionUID = 1L;
	private Point a;
	private Point b;
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param color
	 */
	public Line(Color color) {
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param a
	 * @param b
	 * @param color
	 */
	public Line(Point a, Point b, Color color) {
		this.a = a;
		this.b = b;
		this.color = color;
	}

	/**
	 * Getter for a
	 * 
	 * @return
	 */
	public Point getA() {
		return a;
	}

	/**
	 * Setter for a
	 * 
	 * @param a
	 */
	public void setA(Point a) {
		this.a = a;
	}

	/**
	 * Getter for b
	 * 
	 * @return
	 */
	public Point getB() {
		return b;
	}

	/**
	 * Setter for b
	 * 
	 * @param a
	 */
	public void setB(Point b) {
		this.b = b;
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
	 * @param a
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		// “Line (3,5)-(20,30)”,
		return "Line (" + a.x + "," + a.y + ")-(" + b.x + "," + b.y + ")";
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

}
