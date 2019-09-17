package hr.fer.zemris.java.custom.scripting.node;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class Node defines data structure and a set of methods for working with it.
 * 
 * @author Filip
 */
public abstract class Node {

	private ArrayIndexedCollection col;

	/**
	 * This method adds child to node.
	 * 
	 * @param child
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child, "Node is null.");
		if (col == null) {
			col = new ArrayIndexedCollection();
		}
		col.add(child);
	}

	/**
	 * Returns number of children.
	 * 
	 * @return
	 */
	public int numberOfChildren() {
		return col.size();
	}

	/**
	 * Getter for node child with given index.
	 * 
	 * @param index given index
	 * @return node on position index
	 */
	public Node getChild(int index) {
		return (Node) col.get(index);
	}

	/**
	 * Visitor accept method.
	 * 
	 * @param node
	 */
	public abstract void accept(INodeVisitor node);

}
