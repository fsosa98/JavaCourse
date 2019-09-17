package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantInteger defines data structure and a set of 
 * methods for working with it.
 * 
 * @author Filip
 */
public class ElementConstantInteger extends Element {

	private int value;
	 
	/**
	 * Constructor with given value.
	 * 
	 * @param value given value
	 */
	public ElementConstantInteger(int value) {
		Objects.requireNonNull(value, "Value is null.");
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}
