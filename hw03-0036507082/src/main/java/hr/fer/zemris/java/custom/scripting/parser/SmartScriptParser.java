package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptStates;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.node.EchoNode;
import hr.fer.zemris.java.custom.scripting.node.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.node.Node;
import hr.fer.zemris.java.custom.scripting.node.TextNode;

/**
 * Class SmartScriptParser is parser class for parsing given text with lexer
 * help.
 * 
 * @author Filip
 */
public class SmartScriptParser {

	private SmartScriptLexer lexer;
	private SmartScriptToken token;
	private DocumentNode nodeDocument;
	private ObjectStack stack;

	/**
	 * Constructor which initializes parser with given text.
	 * 
	 * @param text initial text.
	 */
	public SmartScriptParser(String documentBody) {
		Objects.requireNonNull(documentBody, "DocumentBody is null.");
		lexer = new SmartScriptLexer(documentBody);
		nodeDocument = new DocumentNode();
		stack = new ObjectStack();
		stack.push(nodeDocument);

		try {
			parse();
		} catch (Exception exc) {
			throw new SmartScriptParserException(exc.getMessage());
		}
	}

	/**
	 * This method is used for parsing text with lexer help.
	 * 
	 */
	private void parse() {
		while (true) {
			token = lexer.nextToken();
			if (lexer.getState() == SmartScriptStates.TEXT) {

				if (token.getType() == SmartScriptTokenType.EOF) {
					if (stack.size() != 1)
						throw new SmartScriptParserException("Invalid text.");

					return;
				} else if (token.getType() == SmartScriptTokenType.TAG) {
					lexer.setState(SmartScriptStates.TAG);
					continue;
				} else if (token.getType() == SmartScriptTokenType.TEXT) {
					TextNode nodeTxt = new TextNode((String) token.getValue());
					Node node = (Node) stack.peek();
					node.addChildNode(nodeTxt);
				} else
					throw new SmartScriptParserException("Invalid SmartScriptToken.");
			} else if (lexer.getState() == SmartScriptStates.TAG) {

				if (token.getType() == SmartScriptTokenType.EOF)
					throw new SmartScriptParserException("Tag is not closed.");
				else if (token.getType() == SmartScriptTokenType.TEXT)
					throw new SmartScriptParserException("Invalid SmartScriptToken.");
				else if (token.getType() == SmartScriptTokenType.TAG) {
					lexer.setState(SmartScriptStates.TEXT);
					continue;
				} else if (token.getType() == SmartScriptTokenType.TAGNAME) {
					if (((String) token.getValue()).equals("FOR")) {
						parseFor();
					} else if (((String) token.getValue()).equals("=")) {
						parseEq();
					} else if (((String) token.getValue()).equals("END")) {
						stack.pop();
					} else
						throw new SmartScriptParserException("Invalid TAGNAME.");
				} else
					throw new SmartScriptParserException("Invalid TAGNAME.");
			} else
				throw new SmartScriptParserException("Invalid Parser state.");
		}
	}

	/**
	 * Helper method for parsing for.
	 * 
	 */
	private void parseFor() {
		int i = 0;
		token = lexer.nextToken();
		if (token.getType() != SmartScriptTokenType.VARIABLE)
			throw new SmartScriptParserException("Error in for.");
		ElementVariable variable = new ElementVariable((String) token.getValue());

		Element[] elements = new Element[3];
		while (true) {
			token = lexer.nextToken();
			if (token.getType() == SmartScriptTokenType.TAG) {
				lexer.setState(SmartScriptStates.TEXT);
				break;
			} else if (token.getType() == SmartScriptTokenType.VARIABLE) {
				elements[i++] = new ElementVariable((String) token.getValue());
			} else if (token.getType() == SmartScriptTokenType.INTEGER) {
				elements[i++] = new ElementConstantInteger((Integer) token.getValue());
			} else if (token.getType() == SmartScriptTokenType.DOUBLE) {
				elements[i++] = new ElementConstantDouble((Double) token.getValue());
			} else if (token.getType() == SmartScriptTokenType.STRING) {
				elements[i++] = new ElementString((String) token.getValue());
			} else
				throw new SmartScriptParserException("Error in for.");
		}
		if (elements.length < 2)
			throw new SmartScriptParserException("Error in for.");
		if (elements.length == 2) {
			elements[2] = null;
		}

		ForLoopNode nodeFor = new ForLoopNode(variable, elements[0], elements[1], elements[2]);
		Node node = (Node) stack.peek();
		node.addChildNode(nodeFor);
		stack.push(nodeFor);
	}

	/**
	 * Helper method for parsing tag.
	 * 
	 */
	private void parseEq() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		while (true) {
			token = lexer.nextToken();
			if (token.getType() == SmartScriptTokenType.TAG) {
				lexer.setState(SmartScriptStates.TEXT);
				break;
			} else if (token.getType() == SmartScriptTokenType.VARIABLE) {
				col.add(new ElementVariable((String) token.getValue()));
			} else if (token.getType() == SmartScriptTokenType.INTEGER) {
				col.add(new ElementConstantInteger((Integer) token.getValue()));
			} else if (token.getType() == SmartScriptTokenType.DOUBLE) {
				col.add(new ElementConstantDouble((Double) token.getValue()));
			} else if (token.getType() == SmartScriptTokenType.STRING) {
				col.add(new ElementString((String) token.getValue()));
			} else if (token.getType() == SmartScriptTokenType.FUNCTION) {
				col.add(new ElementFunction((String) token.getValue()));
			} else if (token.getType() == SmartScriptTokenType.OPERATOR) {
				col.add(new ElementOperator((String) token.getValue()));
			} else
				throw new SmartScriptParserException("Error in for.");
		}
		Element[] elements = new Element[col.size()];
		for (int i = 0; i < col.size(); i++) {
			elements[i] = (Element) col.get(i);
		}
		EchoNode nodeFor = new EchoNode(elements);
		Node node = (Node) stack.peek();
		node.addChildNode(nodeFor);
	}

	/**
	 * Getter for DocumentNode
	 * 
	 * @return nodeDocument
	 */
	public DocumentNode getDocumentNode() {
		return nodeDocument;
	}

}
