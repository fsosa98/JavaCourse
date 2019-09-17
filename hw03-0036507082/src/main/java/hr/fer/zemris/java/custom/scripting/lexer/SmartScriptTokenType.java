package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Token types that can be used.
 * 
 * @author Filip
 */
public enum SmartScriptTokenType {

	/**
	 * EOF type
	 */
	EOF,

	/**
	 * TEXT type
	 */
	TEXT,

	/**
	 * INTEGER type
	 */
	INTEGER,

	/**
	 * DOUBLE type
	 */
	DOUBLE,

	/**
	 * VARIABLE type
	 */
	VARIABLE,

	/**
	 * FUNCTION type
	 */
	FUNCTION,

	/**
	 * OPERATOR type
	 */
	OPERATOR,

	/**
	 * STRING type
	 */
	STRING,

	/**
	 * TAGNAME type
	 */
	TAGNAME,

	/**
	 * TAG type
	 */
	TAG

}
