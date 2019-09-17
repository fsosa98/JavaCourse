package hr.fer.zemris.java.hw03.prob1.demo;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerState;

/**
 * Class LexerDemo is used for testing Lexer.
 * 
 * @author Filip
 */
public class LexerDemo {

	/**
	 * This is main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Lexer lexer = new Lexer("Janko 3# Ivana26\\a 463abc#zzzz");

		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());

		lexer.setState(LexerState.EXTENDED);

		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());

		lexer.setState(LexerState.BASIC);

		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
	}
}
