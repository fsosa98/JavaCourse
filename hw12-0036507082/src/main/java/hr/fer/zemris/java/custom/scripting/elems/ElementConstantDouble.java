package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble defines data structure and a set of 
 * methods for working with it.
 * 
 * @author Filip
 */
public class ElementConstantDouble extends Element {

	private double value;
	 
	/**
	 * Constructor with given value.
	 * 
	 * @param value given value
	 */
	public ElementConstantDouble(double value) {
		Objects.requireNonNull(value, "Value is null.");
		this.value = value;
	}

	/**
	 * Getter for value.
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
}
