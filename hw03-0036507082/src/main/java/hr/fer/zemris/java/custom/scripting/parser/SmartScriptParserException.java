package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class EmptyStackException represents empty stack exception.
 * 
 * @author Filip
 */

public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Constructor with message. The message will be displayed when the exception is
	 * thrown.
	 * 
	 * @param displayed message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
