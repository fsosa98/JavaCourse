package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class QueryParser is parser class for parsing given text with lexer help.
 * 
 * @author Filip
 */
public class QueryParser {

	private QueryLexer lexer;
	private List<ConditionalExpression> list;
	private QueryToken token;

	/**
	 * Constructor which initializes parser with given text.
	 * 
	 * @param text initial text.
	 */
	public QueryParser(String text) {
		lexer = new QueryLexer(text);
		list = new ArrayList<>();

		try {
			parse();
		} catch (Exception exc) {
			throw new QueryException(exc.getMessage());
		}
	}

	/**
	 * This method is used for parsing text with lexer help.
	 * 
	 */
	public void parse() {
		while (true) {
			token = lexer.nextToken();

			if (token.getType() != QueryTokenType.VARIABLE) {
				throw new QueryException("Unable to parse.");
			}
			String var = (String) token.getValue();
			IFieldValueGetter first = fieldValueGetter(var);

			token = lexer.nextToken();

			if (token.getType() != QueryTokenType.OPERATOR) {
				throw new QueryException("Unable to parse.");
			}
			String operator = (String) token.getValue();
			IComparisonOperator third = comparisonOperator(operator);

			token = lexer.nextToken();

			if (token.getType() != QueryTokenType.STRING) {
				throw new QueryException("Unable to parse.");
			}
			String st = (String) token.getValue();
			String second = st.substring(1, st.length() - 1);

			list.add(new ConditionalExpression(first, second, third));

			token = lexer.nextToken();

			if (token.getType() == QueryTokenType.EOF)
				break;
			if (token.getType() == QueryTokenType.AND)
				continue;

			throw new QueryException("Unable to parse.");
		}
	}

	/**
	 * Helper method for getting comparison operator.
	 * 
	 * @param operator given operator
	 * @return comparison operator
	 */
	private static IComparisonOperator comparisonOperator(String operator) {
		IComparisonOperator third;
		switch (operator) {
		case "<":
			third = ComparisonOperators.LESS;
			break;
		case "<=":
			third = ComparisonOperators.LESS_OR_EQUALS;
			break;
		case ">":
			third = ComparisonOperators.GREATER;
			break;
		case ">=":
			third = ComparisonOperators.GREATER_OR_EQUALS;
			break;
		case "=":
			third = ComparisonOperators.EQUALS;
			break;
		case "!=":
			third = ComparisonOperators.NOT_EQUALS;
			break;
		case "LIKE":
			third = ComparisonOperators.LIKE;
			break;
		default:
			throw new QueryException("Wrong operator name");
		}
		return third;
	}

	/**
	 * Helper method for getting field value.
	 * 
	 * @param var given string
	 * @return field value
	 */
	private static IFieldValueGetter fieldValueGetter(String var) {
		IFieldValueGetter first;
		switch (var) {
		case "jmbag":
			first = FieldValueGetters.JMBAG;
			break;
		case "lastName":
			first = FieldValueGetters.LAST_NAME;
			break;
		case "firstName":
			first = FieldValueGetters.FIRST_NAME;
			break;
		default:
			throw new QueryException("Wrong variable name");
		}
		return first;
	}

	/**
	 * If in querry is only jmbag then querry is direct.
	 * 
	 * @return true if querry is direct. false if querry is not direct.
	 */
	public boolean isDirectQuery() {
		if (list.size() != 1) {
			return false;
		}
		ConditionalExpression query = list.get(0);
		if (query.getFieldGetter() == FieldValueGetters.JMBAG
				&& query.getComparisonOperator() == ComparisonOperators.EQUALS) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns querry jmbag if querry is direct.
	 * 
	 * @return jmbag
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return list.get(0).getStringLiteral();
		}
		throw new IllegalStateException("Query is not direct.");
	}

	/**
	 * This method returns list of expressions.
	 * 
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		return list;
	}

}
