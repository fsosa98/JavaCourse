package hr.fer.zemris.java.hw06.shell.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class NameBuilderParser is parser class for parsing given text with lexer
 * help.
 * 
 * @author Filip
 */
public class NameBuilderParser {

	private NameBuilder nameBuilder;
	private Lexer lexer;

	/**
	 * Constructor which initializes parser with given text.
	 * 
	 * @param izraz initial text.
	 */
	public NameBuilderParser(String izraz) {
		this.lexer = new Lexer(izraz);
		try {
			parse();
		} catch (Exception exc) {
			System.out.println("Wrong argument.");
		}
	}

	/**
	 * This method is used for parsing text with lexer help.
	 * 
	 */
	private void parse() {
		List<NameBuilder> list = new ArrayList<NameBuilder>();
		int k = 0;
		Token token = lexer.nextToken();
		while (token.getType() != TokenType.EOF) {
			k++;
			if (k > 15) {
				return;
			}
			if (token.getType() == TokenType.STRING) {
				list.add(new StringNameBuilder((String) token.getValue()));
				lexer.setState(LexerState.COMMAND);
			} else if (token.getType() == TokenType.OPEN) {
				token = lexer.nextToken();
				String groupIndexString = (String) token.getValue();
				char padding;
				int minWidth = 5;
				int groupIndex = Integer.parseInt(groupIndexString);

				token = lexer.nextToken();
				if (token.getType() == TokenType.CLOSE) {
					list.add(new GroupNameBuilder(groupIndex));
				} else if (token.getType() == TokenType.COMMA) {
					token = lexer.nextToken();
					if (token.getType() != TokenType.STRING) {
						throw new RuntimeException();
					}
					String arg = (String) token.getValue();

					if (arg.length() == 1) {
						minWidth = Integer.parseInt(arg);
						padding = ' ';
					} else {
						padding = arg.charAt(0);
						minWidth = Integer.parseInt(arg.substring(1));
					}

					token = lexer.nextToken();
					if (token.getType() != TokenType.CLOSE) {
						throw new RuntimeException();
					}
					list.add(new GroupPaddingNameBuilder(groupIndex, padding, minWidth));
				} else {
					throw new RuntimeException();
				}
				if (groupIndex < 0 || minWidth < 0) {
					throw new RuntimeException();
				}
				lexer.setState(LexerState.STRING);
			} else {
				throw new RuntimeException();
			}
			token = lexer.nextToken();
		}
		nameBuilder = new CompositionNameBuilder(list);
	}

	/**
	 * Getter for builder.
	 * 
	 * @return builder
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}

}
