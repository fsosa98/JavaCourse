package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementFunction defines data structure and a set of methods for working
 * with it.
 * 
 * @author Filip
 */
public class ElementFunction extends Element {

	private String name;

	/**
	 * Constructor with given name.
	 * 
	 * @param value given name
	 */
	public ElementFunction(String name) {
		Objects.requireNonNull(name, "Name is null.");
		this.name = name;
	}

	/**
	 * Getter for name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return name;
	}

}
