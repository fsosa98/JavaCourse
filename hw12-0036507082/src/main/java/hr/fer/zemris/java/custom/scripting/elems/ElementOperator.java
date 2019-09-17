package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementOperator defines data structure and a set of 
 * methods for working with it.
 * 
 * @author Filip
 */
public class ElementOperator extends Element {

	private String symbol;
	 
	/**
	 * Constructor with given symbol.
	 * 
	 * @param value given symbol
	 */
	public ElementOperator(String symbol) {
		Objects.requireNonNull(symbol, "Symbol is null.");
		this.symbol = symbol;
	}
	
	/**
	 * Getter for symbol.
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String asText() {
		return symbol;
	}
	
}
