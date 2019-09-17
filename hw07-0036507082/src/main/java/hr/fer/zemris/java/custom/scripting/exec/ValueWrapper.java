package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class ValueWrapper defines data structure and a set of methods for working
 * with it.
 * 
 * Users can add, subtract, multiply, divide or compare given values.
 * 
 * @author Filip
 */
public class ValueWrapper {

	private Object value;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param value given value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds given value.
	 * 
	 * @param incValue
	 */
	public void add(Object incValue) {
		operation((a, b) -> a + b, incValue);
	}

	/**
	 * Subtracts given value.
	 * 
	 * @param incValue
	 */
	public void subtract(Object decValue) {
		operation((a, b) -> a - b, decValue);
	}

	/**
	 * Multiplies given value.
	 * 
	 * @param incValue
	 */
	public void multiply(Object mulValue) {
		operation((a, b) -> a * b, mulValue);
	}

	/**
	 * Divides given value.
	 * 
	 * @param incValue
	 */
	public void divide(Object divValue) {
		operation((a, b) -> a / b, divValue);
	}

	/**
	 * Compares given value.
	 * 
	 * @param incValue
	 */
	public int numCompare(Object withValue) {
		return Double.compare(getValue(value), getValue(withValue));
	}

	/**
	 * Getter for value.
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for value
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Helper method for applying operations.
	 * 
	 * @param operation
	 * @param value
	 */
	public void operation(BiFunction<Double, Double, Double> operation, Object value) {
		try {
			var type1 = getType(this.value);
			double value1 = getValue(this.value);
			var type2 = getType(value);
			double value2 = getValue(value);

			double result = operation.apply(value1, value2);

			if (type1 == Double.TYPE || type2 == Double.TYPE) {
				this.value = result;
			} else {
				this.value = (int) result;
			}

		} catch (Exception exc) {
			throw new RuntimeException("Value type is not valid.");
		}
	}

	/**
	 * Method returns type of value.
	 * 
	 * @param value
	 * @return
	 */
	public Class<?> getType(Object value) {
		if (value == null || value instanceof Integer) {
			return Integer.TYPE;
		} else if (value instanceof Double) {
			return Double.TYPE;
		} else if (value instanceof String) {
			String input = (String) value;
			if (input.contains(".") || input.contains("E")) {
				return Double.TYPE;
			} else {
				return Integer.TYPE;
			}
		} else {
			throw new RuntimeException("Value type is not valid.");
		}
	}

	/**
	 * Method returns double value of given value.
	 * 
	 * @param value
	 * @return
	 */
	public double getValue(Object value) {
		if (value == null) {
			return 0.0;
		} else if (value instanceof Double || value instanceof Integer) {
			return Double.valueOf(value.toString());
		} else if (value instanceof String) {
			String input = (String) value;
			if (input.contains(".") || input.contains("E")) {
				return (double) Double.valueOf(input);
			} else {
				return (double) Integer.valueOf(input);
			}
		} else {
			throw new RuntimeException("Value type is not valid.");
		}
	}

}
