package hr.fer.zemris.java.hw05.db;

/**
 * Class ComparisonOperators represents operators.
 * 
 * @author Filip
 */
public class ComparisonOperators {

	/**
	 * LESS operator.
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;

	/**
	 * LESS_OR_EQUALS operator.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;

	/**
	 * GREATER operator.
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;

	/**
	 * GREATER_OR_EQUALS operator.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;

	/**
	 * EQUALS operator.
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;

	/**
	 * NOT_EQUALS operator.
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;

	/**
	 * LIKE operator.
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> s1.matches(s2.replace("*", "(.*)"));

}
