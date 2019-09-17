package hr.fer.zemris.java.hw03.prob1;

/**
 * Class Token is class for token.
 * 
 * @author Filip
 */
public class Token {

	private TokenType type;
	private Object value;

	/**
	 * Constructor which initializes Token with given type and value.
	 * 
	 * @param type  given type.
	 * @param value given value
	 */
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return type;
	}

}
