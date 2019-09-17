package hr.fer.zemris.java.custom.collections;

/**
 * Class EmptyStackException represents empty stack exception.
 * 
 * @author Filip
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
