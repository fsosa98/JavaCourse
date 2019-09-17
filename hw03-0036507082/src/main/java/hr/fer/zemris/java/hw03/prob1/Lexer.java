package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Class Lexer is lexer class.
 * 
 * @author Filip
 */
public class Lexer {

	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	/**
	 * Constructor which initializes lexer with given text.
	 * 
	 * @param text initial text.
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		setState(LexerState.BASIC);
	}

	/**
	 * Returns the next element in the text.
	 * 
	 * @return next element
	 */
	public Token nextToken() {

		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}
		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		StringBuilder sb = new StringBuilder();

		if (state == LexerState.BASIC) {

			// WORD
			while (true) {
				if (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
					append(sb);
				} else if (currentIndex < data.length && data[currentIndex] == '\\') {
					currentIndex++;
					if (currentIndex >= data.length || Character.isLetter(data[currentIndex])) {
						throw new LexerException("Invalid Escape.");
					}
					append(sb);
				} else {
					if (sb.length() != 0) {
						return new Token(TokenType.WORD, new String(sb));
					} else
						break;
				}
			}

			// NUMBER
			while (true) {
				if (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					append(sb);
				} else {
					if (sb.length() != 0) {
						try {
							Long tokValue = Long.parseLong(new String(sb));
							return new Token(TokenType.NUMBER, tokValue);
						} catch (NumberFormatException exc) {
							throw new LexerException("Input is incorrect.");
						}
					} else
						break;
				}
			}

			// SYMBOL
			return new Token(TokenType.SYMBOL, data[currentIndex++]);
		} else {
			if (data[currentIndex] == '#') {
				return new Token(TokenType.SYMBOL, data[currentIndex++]);
			}
			while (true) {
				if (currentIndex >= data.length || data[currentIndex] == ' ' || data[currentIndex] == '#')
					break;
				append(sb);
			}
			return new Token(TokenType.WORD, new String(sb));
		}

	}

	/**
	 * Returns the current token in the lexer.
	 * 
	 * @return current token
	 */
	public Token getToken() {
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

	/**
	 * This is helper method for adding characters to given StringBuilder.
	 * 
	 * @param sb given StringBuilder
	 */
	private void append(StringBuilder sb) {
		sb.append(data[currentIndex]);
		currentIndex++;
	}

	/**
	 * Setter for lexer state.
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state, "State is null.");
		this.state = state;
	}
}
