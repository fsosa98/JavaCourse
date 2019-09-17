package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class ConditionalExpression represents expressions.
 * 
 * Users can get field value getter, string literal and comparison operator.
 * 
 * @author Filip
 */
public class ConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param fieldGetter given fieldGetter
	 * @param stringLiteral given stringLiteral
	 * @param comparisonOperator given comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		Objects.requireNonNull(fieldGetter, "ValueGetter is null.");
		Objects.requireNonNull(stringLiteral, "Literal is null.");
		Objects.requireNonNull(comparisonOperator, "Operator is null.");

		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for fieldGetter.
	 * 
	 * @return fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for stringLiteral.
	 * 
	 * @return stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comparisonOperator.
	 * 
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
