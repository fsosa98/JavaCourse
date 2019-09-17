package hr.fer.zemris.java.hw05.db;

/**
 * Interface IComparisonOperator defines a single method which can be used for testing given
 * strings.
 * 
 * @author Filip
 */
public interface IComparisonOperator {

	/**
	 * This method is used for testing given strings.
	 * 
	 * @param value1 given string
	 * @param value2 given string
	 * @return true if satisfy the test, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
	
}
