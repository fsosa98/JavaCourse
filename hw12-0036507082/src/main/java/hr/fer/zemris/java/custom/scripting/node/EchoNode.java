package hr.fer.zemris.java.custom.scripting.node;

import java.util.Objects;
import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class EchoNode defines data structure and a set of 
 * methods for working with it.
 * 
 * @author Filip
 */
public class EchoNode extends Node {

	private Element[] elements;

	/**
	 * Constructor with given elements array.
	 * 
	 * @param elements given elements
	 */
	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements, "Element is null.");
		this.elements = elements;
	}
	
	/**
	 * Getter for elements array.
	 * 
	 * @return elements array
	 */
	public Element[] getElements() {
		return elements;
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
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
	
}
