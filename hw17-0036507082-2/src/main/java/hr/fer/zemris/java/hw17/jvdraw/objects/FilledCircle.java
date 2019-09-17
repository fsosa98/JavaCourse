package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Class FilledCircle is implementation of GeometricalObject filled circle.
 * 
 * @author Filip
 */
public class FilledCircle extends GeometricalObject {

	private static final long serialVersionUID = 1L;
	private Point s;
	private int r;
	private Color colorLine;
	private Color colorFill;

	/**
	 * Constructor
	 * 
	 * @param colorLine
	 * @param colorFill
	 */
	public FilledCircle(Color colorLine, Color colorFill) {
		this.colorLine = colorLine;
		this.colorFill = colorFill;
	}

	/**
	 * Constructor
	 * 
	 * @param s
	 * @param r
	 * @param colorLine
	 * @param colorFill
	 */
	public FilledCircle(Point s, int r, Color colorLine, Color colorFill) {
		this.s = s;
		this.r = r;
		this.colorLine = colorLine;
		this.colorFill = colorFill;
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
	public Color getColorLine() {
		return colorLine;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 */
	public void setColorLine(Color colorLine) {
		this.colorLine = colorLine;
	}

	/**
	 * Getter for color
	 * 
	 * @return
	 */
	public Color getColorFill() {
		return colorFill;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 */
	public void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}

	@Override
	public String toString() {
		// “Line (3,5)-(20,30)”,
		return "Circle (" + s.x + "," + s.y + "), " + r + ", "
				+ String.format("#%02X%02X%02X", colorFill.getRed(), colorFill.getGreen(), colorFill.getBlue());
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

}
