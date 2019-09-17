package hr.fer.zemris.java.hw05.db;

/**
 * Class QueryException represents lexer exception.
 * 
 * @author Filip
 */

public class QueryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public QueryException() {
	}
	
	/**
	 * Constructor with message.
	 * The message will be displayed when the exception is thrown.
	 * 
	 * @param displayed message 
	 */
	public QueryException(String message) {
		super(message);
	}
}
