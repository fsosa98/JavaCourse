package hr.fer.zemris.java.custom.scripting.node;

import java.util.Objects;

/**
 * Class TextNode defines data structure and a set of methods for working with
 * it.
 * 
 * @author Filip
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructor with given text.
	 * 
	 * @param value given text
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text, "Text is null.");
		this.text = text;
	}

	/**
	 * Getter for text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildNode(Node child) {
		throw new RuntimeException("EchoNode can't have children.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numberOfChildren() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Node getChild(int index) {
		throw new RuntimeException("EchoNode can't have children.");
	}

}
