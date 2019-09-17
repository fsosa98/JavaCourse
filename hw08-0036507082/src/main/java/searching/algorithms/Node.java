package searching.algorithms;

import java.util.Objects;

/**
 * Class Node defines data structure and a set of methods for working with it.
 * 
 * Users can get state, parents and cost of node.
 * 
 * @author Filip
 * @param <S>
 */
public class Node<S> {

	private Node<S> parent;
	private S state;
	private double cost;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param parent given parents
	 * @param state  given state
	 * @param cost   given cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for state.
	 * 
	 * @return
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter for cost.
	 * 
	 * @return
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Getter for parent.
	 * 
	 * @return
	 */
	public Node<S> getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		@SuppressWarnings("rawtypes")
		Node other = (Node) obj;
		return state.equals(other.state);
	}

}
