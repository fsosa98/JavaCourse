package hr.fer.zemris.java.hw06.shell.builder;

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
		setState(LexerState.STRING);
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

		if (state == LexerState.STRING) {
			while (true) {
				if (currentIndex >= data.length) {
					return new Token(TokenType.STRING, new String(sb));
				}
				if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
					return new Token(TokenType.STRING, new String(sb));
				}
				sb.append(data[currentIndex++]);
			}
		} else {

			if (currentIndex >= data.length) {
				throw new LexerException();
			}

			// OPEN
			if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
				currentIndex += 2;
				return new Token(TokenType.OPEN, "${");
			}

			// CLOSE
			if (data[currentIndex] == '}') {
				currentIndex++;
				return new Token(TokenType.CLOSE, "}");
			}

			// COMMA
			if (data[currentIndex] == ',') {
				currentIndex++;
				return new Token(TokenType.COMMA, ",");
			}

			// STRING
			while (true) {
				sb.append(data[currentIndex++]);
				if (data[currentIndex] == ',' || data[currentIndex] == '}' || data[currentIndex] == ' ') {
					return new Token(TokenType.STRING, new String(sb));
				}
			}
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
	 * Setter for lexer state.
	 * 
	 * @param state given state
	 */
	public void setState(LexerState state) {
		this.state = state;
	}

}
