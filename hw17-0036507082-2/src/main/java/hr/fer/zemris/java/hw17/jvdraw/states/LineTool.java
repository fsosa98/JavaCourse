package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;

/**
 * Class LineTool is implementation of line tool.
 * 
 * @author Filip
 */
public class LineTool implements Tool {

	private DrawingModel model;
	private IColorProvider provider;
	private Line line;
	private int count = 0;

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param provider
	 */
	public LineTool(DrawingModel model, IColorProvider provider) {
		this.model = model;
		this.provider = provider;
		line = new Line(provider.getCurrentColor());
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (count == 0) {
			line.setColor(provider.getCurrentColor());
			line.setA(e.getPoint());
			count++;
		} else {
			line.setB(e.getPoint());
			model.add(line);
			line = new Line(provider.getCurrentColor());
			count = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (count != 0) {
			line.setB(e.getPoint());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (count != 0 && line.getB() != null) {
			g2d.setColor(line.getColor());
			g2d.drawLine(line.getA().x, line.getA().y, line.getB().x, line.getB().y);
		}
	}

}
