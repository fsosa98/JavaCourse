package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class LexerException represents lexer exception.
 * 
 * @author Filip
 */

public class SmartScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public SmartScriptLexerException() {
	}
	
	/**
	 * Constructor with message.
	 * The message will be displayed when the exception is thrown.
	 * 
	 * @param displayed message 
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
}
