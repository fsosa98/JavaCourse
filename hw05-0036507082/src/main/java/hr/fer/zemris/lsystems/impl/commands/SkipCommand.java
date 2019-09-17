package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class ColorCommand defines data structure for color command.
 * 
 * @author Filip
 */
public class SkipCommand implements Command {

	private double step;

	/**
	 * Constructor with given step.
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
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

		ts.setPosition(position.translated(direction.scaled(step * shift)));
	}

}
