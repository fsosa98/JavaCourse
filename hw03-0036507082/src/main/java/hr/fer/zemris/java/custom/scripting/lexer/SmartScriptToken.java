package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class SmartScriptToken is class for token.
 * 
 * @author Filip
 */
public class SmartScriptToken {

	private SmartScriptTokenType type;
	private Object value;

	/**
	 * Constructor which initializes SmartScriptToken with given type and value.
	 * 
	 * @param type  given type.
	 * @param value given value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for type.
	 * 
	 * @return type
	 */
	public SmartScriptTokenType getType() {
		return type;
	}

}
