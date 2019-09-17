package hr.fer.zemris.lsystems.impl;

/**
 * Class LSystemException represents LSystem exception.
 * 
 * @author Filip
 */

public class LSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LSystemException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public LSystemException(String message) {
		super(message);
	}
}
