package hr.fer.zemris.java.custom.collections;

/**
 * Interface Tester defines a single method which can be used for testing given
 * Object.
 * 
 * @author Filip
 */
public interface Tester<T> {

	/**
	 * This method is used for testing given Object.
	 * 
	 * @param obj given object
	 * @return true if satisfy the test, false otherwise
	 */
	public boolean test(T obj);

}
