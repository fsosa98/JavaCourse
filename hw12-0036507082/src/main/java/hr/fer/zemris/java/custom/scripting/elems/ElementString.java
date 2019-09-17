package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementString defines data structure and a set of 
 * methods for working with it.
 * 
 * @author Filip
 */
public class ElementString extends Element {

	private String value;
	 
	/**
	 * Constructor with given value.
	 * 
	 * @param value given value
	 */
	public ElementString(String value) {
		Objects.requireNonNull(value, "Value is null.");
		value = value.substring(1,value.length()-1);
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String asText() {
		return value;
	}
	
}
