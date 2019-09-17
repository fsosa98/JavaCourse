package hr.fer.zemris.java.hw07.observer2;

import java.util.Objects;

/**
 * Class IntegerStorageChange defines data structure and a set of methods for
 * working with it.
 * 
 * @author Filip
 */
public class IntegerStorageChange {

	private IntegerStorage istorage;
	private int value;
	private int newValue;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param istorage
	 * @param value
	 * @param newValue
	 */
	public IntegerStorageChange(IntegerStorage istorage, int value, int newValue) {
		Objects.requireNonNull(istorage);
		this.istorage = istorage;
		this.value = value;
		this.newValue = newValue;
	}

	/**
	 * Getter for storage.
	 * 
	 * @return
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Getter for value.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Getter for new value.
	 * 
	 * @return
	 */
	public int getNewValue() {
		return newValue;
	}

}
