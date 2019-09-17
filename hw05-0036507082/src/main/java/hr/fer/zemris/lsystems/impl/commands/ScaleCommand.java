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
public class ScaleCommand implements Command {

	private double factor;

	/**
	 * Constructor with given factor.
	 * 
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState ts = ctx.getCurrentState();
		ts.setShiftLength(ts.getShiftLength() * factor);

	}

}
