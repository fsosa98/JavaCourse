package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Class CircleTool is implementation of circle tool.
 * 
 * @author Filip
 */
public class CircleTool implements Tool {

	private DrawingModel model;
	private IColorProvider provider;
	private Circle circle;
	private int count = 0;

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param provider
	 */
	public CircleTool(DrawingModel model, IColorProvider provider) {
		this.model = model;
		this.provider = provider;
		circle = new Circle(provider.getCurrentColor());
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Method for calculating distance
	 * 
	 * @param s
	 * @param point
	 * @return
	 */
	private int calculateDist(Point s, Point point) {
		return (int) Math.sqrt(Math.pow(s.getX() - point.getX(), 2) + Math.pow(s.getY() - point.getY(), 2));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (count == 0) {
			circle.setColor(provider.getCurrentColor());
			circle.setS(e.getPoint());
			count++;
		} else {
			circle.setR(calculateDist(circle.getS(), e.getPoint()));
			model.add(circle);
			circle = new Circle(provider.getCurrentColor());
			count = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (count != 0) {
			circle.setR(calculateDist(circle.getS(), e.getPoint()));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (count != 0 && circle.getR() != 0) {
			g2d.setColor(circle.getColor());
			g2d.drawOval(circle.getS().x - circle.getR(), circle.getS().y - circle.getR(), circle.getR() * 2,
					circle.getR() * 2);
		}
	}

}
