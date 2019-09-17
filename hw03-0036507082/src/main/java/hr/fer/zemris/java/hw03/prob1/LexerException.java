package hr.fer.zemris.java.hw03.prob1;

/**
 * Class LexerException represents lexer exception.
 * 
 * @author Filip
 */

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LexerException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public LexerException(String message) {
		super(message);
	}

}
