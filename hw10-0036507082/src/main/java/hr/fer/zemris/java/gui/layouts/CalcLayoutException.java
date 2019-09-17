package hr.fer.zemris.java.gui.layouts;

/**
 * Class CalcLayoutException represents empty stack exception.
 * 
 * @author Filip
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CalcLayoutException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}