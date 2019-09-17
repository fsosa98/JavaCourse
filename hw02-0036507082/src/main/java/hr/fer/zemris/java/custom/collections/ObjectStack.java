package hr.fer.zemris.java.custom.collections;

/**
 * Class ObjectStack defines data structure and a set of 
 * methods for working with it.
 * Class uses stack for storing elements.
 * 
 * Users can get size of collection, push elements, peek elements, 
 * pop element, check if stack is empty or clear stack.
 * 
 * @author Filip
 */
public class ObjectStack {
	
	private ArrayIndexedCollection col;
	
	/**
	 * Default constructor.
	 */
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	/**
	 * This method checks if collection is empty.
	 * 
	 * @return  true if collection is empty
	 * 			false if collection is not empty
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * This method calculates the size of collection.
	 * 
	 * @return number of currently stored objects in this collections.
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * This method pushes given value on the stack. 
	 * 
	 * @param value given value
	 */
	public void push(Object value) {
		col.add(value);
	}
	
	/**
	 * This method removes last value pushed on stack 
	 * from stack and returns it.
	 * 
	 * @return last value on stack
	 */
	public Object pop() {
		Object obj = peek();
		col.remove(size() - 1);
		return obj;
	}
	
	/**
	 * This method returns last value pushed on stack 
	 * from stack.
	 * 
	 * @return last value on stack
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}
		return col.get(size() - 1);
	}
	
	/**
	 * This method removes all elements from this collection.
	 * 
	 */
	public void clear() {
		col.clear();
	}
}
