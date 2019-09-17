package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class ObjectMultistack defines data structure and a set of methods for
 * working with it. Class uses stack for storing elements.
 * 
 * Users can push elements, peek elements, pop element, check if stack is empty.
 * 
 * @author Filip
 */
public class ObjectMultistack {

	private Map<String, MultistackEntry> multiStack;

	/**
	 * Default constructor.
	 */
	public ObjectMultistack() {
		this.multiStack = new HashMap<String, MultistackEntry>();
	}

	/**
	 * Class MultistackEntry represents stack entry. It is a basic building element
	 * of this map.
	 *
	 */
	private static class MultistackEntry {

		private ValueWrapper element;
		private MultistackEntry next;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param element
		 * @param next
		 */
		public MultistackEntry(ValueWrapper element, MultistackEntry next) {
			this.element = element;
			this.next = next;
		}
	}

	/**
	 * This method pushes given value on the stack with given key.
	 * 
	 * @param keyName      given key
	 * @param valueWrapper given value
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);

		MultistackEntry entry = multiStack.get(keyName);
		if (entry == null) {
			multiStack.put(keyName, new MultistackEntry(valueWrapper, null));
		} else {
			multiStack.remove(keyName);
			multiStack.put(keyName, new MultistackEntry(valueWrapper, entry));
		}
	}

	/**
	 * This method removes last value pushed on stack with given key from stack and
	 * returns it.
	 * 
	 * @param keyName given key
	 * @return value
	 */
	public ValueWrapper pop(String keyName) {
		MultistackEntry entry = multiStack.get(keyName);
		if (entry == null) {
			throw new EmptyStackException();
		}
		ValueWrapper el = entry.element;
		multiStack.remove(keyName);
		multiStack.put(keyName, entry.next);

		return el;
	}

	/**
	 * This method returns last value pushed on stack with given key from stack.
	 * 
	 * @param keyName given key
	 * @return value
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry entry = multiStack.get(keyName);
		if (entry == null) {
			throw new EmptyStackException();
		}
		return entry.element;
	}

	/**
	 * This method checks if stack with given key is empty.
	 * 
	 * @param keyName given key
	 * @return true if stack is empty false if stack is not empty
	 */
	public boolean isEmpty(String keyName) {
		return multiStack.get(keyName) == null;
	}

}
