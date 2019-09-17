package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class Context defines data structure and a set of methods for working with
 * it.
 * 
 * @author Filip
 */

public class Context {

	private ObjectStack<TurtleState> stack;

	/**
	 * Default constructor.
	 * 
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * This method return current state.
	 * 
	 * @return current state
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * This method add given state on context.
	 * 
	 * @param state given state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * This method delete
	 * 
	 */
	public void popState() {
		stack.pop();
	}

}
