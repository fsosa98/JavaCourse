package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class ColorCommand defines data structure for color command.
 * 
 * @author Filip
 */
public class RotateCommand implements Command {

	private double angle;

	/**
	 * Constructor with given angle.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ts.setDirection(ts.getDirection().rotated(angle));
	}

}
