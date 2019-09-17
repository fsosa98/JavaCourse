package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Interface GeometricalObjectVisitor is Geometrical Object Visitor interface.
 * 
 * @author Filip
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visiting line method
	 * 
	 * @param line
	 */
	public abstract void visit(Line line);

	/**
	 * Visiting circle method
	 * 
	 * @param circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visiting filledCircle method
	 * 
	 * @param filledCircle
	 */
	public abstract void visit(FilledCircle filledCircle);

}
