package hr.fer.zemris.java.custom.collections;

/**
 * Class Processor defines a single method which can be used for doing something
 * with given Object.
 * 
 * @author Filip
 */

public interface Processor<T> {

	/**
	 * This method is used for doing something with given Object.
	 * 
	 * @param value Object
	 */
	public void process(T value);

}
