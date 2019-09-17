package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface Command defines a single method for executing.
 * 
 * @author Filip
 */
public interface Command {

	/**
	 * This method is used for executing command.
	 * 
	 * @param ctx     given context
	 * @param painter given painter
	 */
	void execute(Context ctx, Painter painter);

}
