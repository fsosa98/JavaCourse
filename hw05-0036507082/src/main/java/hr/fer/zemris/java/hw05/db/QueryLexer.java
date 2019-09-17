package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class QueryLexer is lexer class.
 * 
 * @author Filip
 */

public class QueryLexer {

	private char[] data;
	private QueryToken token;
	private int currentIndex;

	/**
	 * Constructor which initializes lexer with given text.
	 * 
	 * @param text initial text.
	 */
	public QueryLexer(String text) {
		Objects.requireNonNull((Object) text, "Error");
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Returns the next element in the text.
	 * 
	 * @return next element
	 */
	public QueryToken nextToken() {
		skipBlanks();
		
		if (token != null && token.getType() == QueryTokenType.EOF) {
			throw new QueryException("No tokens available.");
		}

		if (currentIndex >= data.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			return token;
		}

		StringBuilder sb = new StringBuilder();

		while (true) {

			// OPERATOR1
			if (currentIndex + 1 < data.length && ((data[currentIndex] == '<' && data[currentIndex + 1] == '=')
					|| (data[currentIndex] == '>' && data[currentIndex + 1] == '=')
					|| (data[currentIndex] == '!' && data[currentIndex + 1] == '='))) {
				sb.append(data[currentIndex++]);
				sb.append(data[currentIndex++]);
				token = new QueryToken(QueryTokenType.OPERATOR, new String(sb));
				return token;
			}

			// OPERATOR2
			if (data[currentIndex] == '=' || data[currentIndex] == '<' || data[currentIndex] == '>') {
				sb.append(data[currentIndex++]);
				token = new QueryToken(QueryTokenType.OPERATOR, new String(sb));
				return token;
			}

			// STRING
			if (data[currentIndex] == '"') {
				sb.append(data[currentIndex++]);
				while (true) {
					if (data[currentIndex] == '"') {
						sb.append(data[currentIndex++]);
						token = new QueryToken(QueryTokenType.STRING, new String(sb));
						return token;
					}
					sb.append(data[currentIndex++]);
				}
			}

			// AND
			if (Character.toUpperCase(data[currentIndex]) == 'A') {
				if (currentIndex + 1 < data.length && Character.toUpperCase(data[currentIndex + 1]) == 'N') {
					if (currentIndex + 2 < data.length && Character.toUpperCase(data[currentIndex + 2]) == 'D') {
						sb.append(data[currentIndex++]);
						sb.append(data[currentIndex++]);
						sb.append(data[currentIndex++]);
						token = new QueryToken(QueryTokenType.AND, new String(sb));
						return token;
					}
				}
			}

			// LIKE
			if (currentIndex + 3 < data.length && Character.toUpperCase(data[currentIndex]) == 'L'
					&& Character.toUpperCase(data[currentIndex + 1]) == 'I'
					&& Character.toUpperCase(data[currentIndex + 2]) == 'K'
					&& Character.toUpperCase(data[currentIndex + 3]) == 'E') {
				sb.append(data[currentIndex++]);
				sb.append(data[currentIndex++]);
				sb.append(data[currentIndex++]);
				sb.append(data[currentIndex++]);
				token = new QueryToken(QueryTokenType.OPERATOR, new String(sb));
				return token;
			}

			// VARIABLE
			if (Character.isLetter(data[currentIndex])) {
				do {
					sb.append(data[currentIndex++]);
				} while (currentIndex < data.length && Character.isLetter(data[currentIndex]));
				token = new QueryToken(QueryTokenType.VARIABLE, new String(sb));
				return token;
			}

			throw new QueryException("Invalid expression.");
		}
	}

	/**
	 * Returns the current token in the lexer.
	 * 
	 * @return current token
	 */
	public QueryToken getToken() {
		return token;
	}

	/**
	 * This method skips blanks to the next character.
	 * 
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
			} else
				break;
		}
	}

}
