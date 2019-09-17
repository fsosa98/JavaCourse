package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class GeometricalObjectBBCalculator is implementation of
 * GeometricalObjectVisitor.
 * 
 * @author Filip
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	private int minX = Integer.MAX_VALUE;
	private int maxX = 0;
	private int minY = Integer.MAX_VALUE;
	private int maxY = 0;

	@Override
	public void visit(Line line) {
		minX = Math.min(Math.min(line.getA().x, line.getB().x), minX);
		minY = Math.min(Math.min(line.getA().y, line.getB().y), minY);

		maxX = Math.max(Math.max(line.getA().x, line.getB().x), maxX);
		maxY = Math.max(Math.max(line.getA().y, line.getB().y), maxY);
	}

	@Override
	public void visit(Circle circle) {
		minX = Math.min(Math.min(circle.getS().x - circle.getR(), circle.getS().x + circle.getR()), minX);
		minY = Math.min(Math.min(circle.getS().y - circle.getR(), circle.getS().y + circle.getR()), minY);

		maxX = Math.max(Math.max(circle.getS().x - circle.getR(), circle.getS().x + circle.getR()), maxX);
		maxY = Math.max(Math.max(circle.getS().y - circle.getR(), circle.getS().y + circle.getR()), maxY);

		if (minX < 0) {
			minX = 0;
		}
		if (minY < 0) {
			minY = 0;
		}
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		minX = Math.min(
				Math.min(filledCircle.getS().x - filledCircle.getR(), filledCircle.getS().x + filledCircle.getR()),
				minX);
		minY = Math.min(
				Math.min(filledCircle.getS().y - filledCircle.getR(), filledCircle.getS().y + filledCircle.getR()),
				minY);

		maxX = Math.max(
				Math.max(filledCircle.getS().x - filledCircle.getR(), filledCircle.getS().x + filledCircle.getR()),
				maxX);
		maxY = Math.max(
				Math.max(filledCircle.getS().y - filledCircle.getR(), filledCircle.getS().y + filledCircle.getR()),
				maxY);

		if (minX < 0) {
			minX = 0;
		}
		if (minY < 0) {
			minY = 0;
		}
	}

	/**
	 * Getter for rectangle
	 * 
	 * @return
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

}
