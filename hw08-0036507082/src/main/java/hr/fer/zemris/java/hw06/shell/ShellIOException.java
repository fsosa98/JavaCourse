package hr.fer.zemris.java.hw06.shell;

/**
 * Class ShellIOException represents empty stack exception.
 * 
 * @author Filip
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ShellIOException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
