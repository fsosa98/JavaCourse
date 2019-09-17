package hr.fer.zemris.java.hw05.db;

/**
 * Class QueryToken is class for token.
 * 
 * @author Filip
 */

public class QueryToken {

	private QueryTokenType type;
	private Object value;

	/**
	 * Constructor which initializes SmartScriptToken with given type and value.
	 * 
	 * @param type  given type.
	 * @param value given value
	 */
	public QueryToken(QueryTokenType type, Object value) {
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
	public QueryTokenType getType() {
		return type;
	}

}
