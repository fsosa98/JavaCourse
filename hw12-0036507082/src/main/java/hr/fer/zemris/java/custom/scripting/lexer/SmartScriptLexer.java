package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Class SmartScriptLexer is lexer class.
 * 
 * @author Filip
 */
public class SmartScriptLexer {

	private char[] data;      
	private SmartScriptToken token;        
	private int currentIndex;     
	private SmartScriptStates state;
	
	/**
	 * Constructor which initializes lexer with given text.
	 * 
	 * @param text initial text.
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull((Object)text, "Error");
		data = text.toCharArray();
		currentIndex = 0;
		setState(SmartScriptStates.TEXT);
	}
	
	/**
	 * Returns the next element in the text.
	 * 
	 * @return next element
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("No tokens available.");
		}
		
		
		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}
		
		StringBuilder sb = new StringBuilder();
		
		if (state == SmartScriptStates.TEXT) {
			
			//TEXT
			while (true) {
				if (currentIndex < data.length && data[currentIndex] == '{') {
					if (currentIndex + 1 < data.length && data[currentIndex+1] == '$') {
						if (sb.length() != 0) {
							return token = new SmartScriptToken(SmartScriptTokenType.TEXT, new String(sb));
						}else {
							return appendAndCreateToken(sb, SmartScriptTokenType.TAG, 2);
						}
					}if (currentIndex + 1 < data.length && data[currentIndex+1] != '$'){
						sb.append(data[currentIndex++]);
					}
				}
				else if (currentIndex < data.length && data[currentIndex] == '\\') {
					currentIndex++;
					if (currentIndex < data.length && data[currentIndex] == '{') {
						sb.append("\\");
						sb.append(data[currentIndex++]);
					}
					else if (currentIndex < data.length && data[currentIndex] == '\\') {
						sb.append("\\");
						sb.append(data[currentIndex++]);
						continue;
					}else {
						throw new SmartScriptLexerException("Invalid escape.");
					}
				}
				else if (currentIndex < data.length) {
					sb.append(data[currentIndex++]);
				}
				else {
					if (sb.length() != 0) {
						return token = new SmartScriptToken(SmartScriptTokenType.TEXT, new String(sb));
					}
					else break;
				}
			}
		}
		if (state == SmartScriptStates.TAG) {
			
			skipBlanks();
			
			try {
				
				//CLOSE
				if (data[currentIndex] == '$') {
					if (currentIndex + 1 < data.length && data[currentIndex+1] == '}') {
						return appendAndCreateToken(sb, SmartScriptTokenType.TAG, 2);
					}
				}
				
				//FOR
				if (Character.toUpperCase(data[currentIndex]) == 'F'){
					if (currentIndex + 1 < data.length && Character.toUpperCase(data[currentIndex+1]) == 'O'){  
						if (currentIndex + 2 < data.length && Character.toUpperCase(data[currentIndex+2]) == 'R'){
							return appendAndCreateToken(sb, SmartScriptTokenType.TAGNAME, 3);
						}			
					}
				}
				
				//=
				if (data[currentIndex] == '='){
					return appendAndCreateToken(sb, SmartScriptTokenType.TAGNAME, 1);
				}
				
				//END
				if (Character.toUpperCase(data[currentIndex]) == 'E'){
					if (currentIndex + 1 < data.length && Character.toUpperCase(data[currentIndex+1]) == 'N'){  
						if (currentIndex + 2 < data.length && Character.toUpperCase(data[currentIndex+2]) == 'D'){
							return appendAndCreateToken(sb, SmartScriptTokenType.TAGNAME, 3);
						}			
					}
				}
				
				//FUNCTION
				if (data[currentIndex] == '@') {
					if (currentIndex + 1 < data.length && Character.isLetter(data[currentIndex+1])) {
						sb.append(data[currentIndex++]);
						sb.append(data[currentIndex++]);
						while (true) {
							if (data[currentIndex] == ' ' || data[currentIndex] == '\n' || data[currentIndex] == '\r' ||
									data[currentIndex] == '\t') {
								return token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, new String(sb));
							}
							else if (Character.isLetter(data[currentIndex]) || 
									Character.isDigit(data[currentIndex]) || data[currentIndex] == '_') {
								sb.append(data[currentIndex++]);
								continue;
							}else if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex+1] == '}') {
								return token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, new String(sb));
							}
							else {
								throw new SmartScriptLexerException("Invalid function name.");
							}
						}
					}else {
						throw new SmartScriptLexerException("Invalid function name.");
					}
				}
				
				//STRING
				if (data[currentIndex] == '"') {
					sb.append(data[currentIndex++]);
					while (true) {
						if (data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t') {
							sb.append(data[currentIndex++]);
							continue;
						}
						else if (data[currentIndex] == '\\') {
							currentIndex++;
							if (data[currentIndex] == 'r') {
								sb.append("\r");
								currentIndex++;
								continue;
							}
							if (data[currentIndex] == 't') {
								sb.append("\t");
								currentIndex++;
								continue;
							}
							if (data[currentIndex] == 'n') {
								sb.append("\n");
								currentIndex++;
								continue;
							}
							if (data[currentIndex] == '\\' || data[currentIndex] == '"') {
								sb.append(data[currentIndex++]);
								continue;
							}
							else throw new SmartScriptLexerException("Invalid expression.");
						}
						else if (data[currentIndex] != '"') {
							sb.append(data[currentIndex++]);
						}else if (data[currentIndex] == '"') {
							return appendAndCreateToken(sb, SmartScriptTokenType.STRING, 1);
						}else {
							throw new SmartScriptLexerException("Invalid expression.");
						}
					}
				}
				
				//INTEGERorDOUBLE
				if (Character.isDigit(data[currentIndex]) || (currentIndex + 1 < data.length  && data[currentIndex] == '-' 
						&& Character.isDigit(data[currentIndex+1]))) {
					int dot = 0;
					sb.append(data[currentIndex++]);
					while (true) {
						if (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '.' && dot == 0)) {
							if (data[currentIndex] == '.') {
								dot++;
							}
							sb.append(data[currentIndex++]);
						}else break;
					}
					try {
						int numI = Integer.parseInt(new String(sb));
						return token = new SmartScriptToken(SmartScriptTokenType.INTEGER, numI);
					}catch (NumberFormatException e) {
						try {
							double numD = Double.parseDouble(new String(sb));
							return token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, numD); 
						}catch (NumberFormatException exc) {
							throw new SmartScriptLexerException("Invalid expression.");
						}
					}
				}
				
				//OPERATOR
				if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*' || 
						data[currentIndex] == '/' || data[currentIndex] == '^') {
					return appendAndCreateToken(sb, SmartScriptTokenType.OPERATOR, 1);
				}
				
				
				//VARIABLE
				if (Character.isLetter(data[currentIndex])) {
					sb.append(data[currentIndex++]);
					while (true) {
						if (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || 
								Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
							sb.append(data[currentIndex++]);
						}else {
							return token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, new String(sb));
						}
					}
				}
				

				throw new SmartScriptLexerException("Invalid expression.");

			}
			catch (Exception exc) {
				throw new SmartScriptLexerException("Invalid expression.");
			}
			
			
		}
		throw new SmartScriptLexerException("Illegal state."); 
	}
	
	/**
	 * Returns the current token in the lexer.
	 * 
	 * @return current token
	 */
	public SmartScriptToken getToken() {
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
			}else break;
		}
	}
	
	/**
	 * This is helper method for adding 
	 * characters to given StringBuilder.
	 * 
	 * @param sb given StringBuilder
	 * @param type given SmartScriptTokenType
	 * @param n number of character to append
	 * @return new SmartScriptToken
	 */
	private SmartScriptToken appendAndCreateToken(StringBuilder sb, SmartScriptTokenType type, int n) {
		for (int i = 0; i < n; i++) {
			sb.append(data[currentIndex++]);
		}
		return token = new SmartScriptToken(type, new String(sb));
	}	
	
	/**
	 * Setter for lexer state.
	 * 
	 * @param state
	 */
	public void setState(SmartScriptStates state) {
		Objects.requireNonNull(state, "State is null.");
		this.state = state;
	}
	
	/**
	 * Getter for lexer state.
	 * 
	 * @return state
	 */
	public SmartScriptStates getState() {
        return state;
	}
	
}
