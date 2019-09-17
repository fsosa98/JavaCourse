package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class DrawCommand defines data structure for draw command.
 * 
 * @author Filip
 */
public class DrawCommand implements Command {

	private double step;

	/**
	 * Constructor with given step.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		double shift = ts.getShiftLength();
		Vector2D position = ts.getPosition();
		Vector2D direction = ts.getDirection();

		Vector2D next = position.translated(direction.scaled(step * shift));
		painter.drawLine(position.getX(), position.getY(), next.getX(), next.getY(), ts.getColor(), 1f);
		ts.setPosition(next);
	}

}
