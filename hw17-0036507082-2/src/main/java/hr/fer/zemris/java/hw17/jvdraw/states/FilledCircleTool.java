package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Class FilledCircleTool is implementation of filled circle tool.
 * 
 * @author Filip
 */
public class FilledCircleTool implements Tool {

	private DrawingModel model;
	private IColorProvider provider1;
	private IColorProvider provider2;
	private FilledCircle circle;
	private int count = 0;

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param provider1
	 * @param provider2
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider provider1, IColorProvider provider2) {
		this.model = model;
		this.provider1 = provider1;
		this.provider2 = provider2;
		circle = new FilledCircle(provider1.getCurrentColor(), provider2.getCurrentColor());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

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
			circle.setColorLine(provider1.getCurrentColor());
			circle.setColorFill(provider2.getCurrentColor());
			circle.setS(e.getPoint());
			count++;
		} else {
			circle.setR(calculateDist(circle.getS(), e.getPoint()));
			model.add(circle);
			circle = new FilledCircle(provider1.getCurrentColor(), provider2.getCurrentColor());
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
			g2d.setColor(circle.getColorFill());
			g2d.fillOval(circle.getS().x - circle.getR(), circle.getS().y - circle.getR(), circle.getR() * 2,
					circle.getR() * 2);
			g2d.setColor(circle.getColorLine());
			g2d.drawOval(circle.getS().x - circle.getR(), circle.getS().y - circle.getR(), circle.getR() * 2,
					circle.getR() * 2);
		}
	}

}
