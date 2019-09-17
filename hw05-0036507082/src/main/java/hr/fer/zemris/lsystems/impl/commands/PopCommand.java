package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class PopCommand defines data structure for pop command.
 * 
 * @author Filip
 */
public class PopCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
