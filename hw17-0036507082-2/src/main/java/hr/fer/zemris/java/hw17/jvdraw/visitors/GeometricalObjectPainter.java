package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class GeometricalObjectPainter is implementation of GeometricalObjectVisitor.
 * 
 * @author Filip
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	private Graphics2D g2d;

	/**
	 * Constructor
	 * 
	 * @param g2d
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getA().x, line.getA().y, line.getB().x, line.getB().y);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getS().x - circle.getR(), circle.getS().y - circle.getR(), circle.getR() * 2,
				circle.getR() * 2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getColorFill());
		g2d.fillOval(filledCircle.getS().x - filledCircle.getR(), filledCircle.getS().y - filledCircle.getR(),
				filledCircle.getR() * 2, filledCircle.getR() * 2);
		g2d.setColor(filledCircle.getColorLine());
		g2d.drawOval(filledCircle.getS().x - filledCircle.getR(), filledCircle.getS().y - filledCircle.getR(),
				filledCircle.getR() * 2, filledCircle.getR() * 2);
	}

}
