package hr.fer.zemris.java.custom.collections;

/**
 * Interface List defines data structure and a set of methods for working with
 * it.
 * 
 * Users can remove element from list, get element on index, insert elements,
 * get index of some element.
 * 
 * @author Filip
 */
public interface List<T> extends Collection<T> {

	/**
	 * This method returns the object that is stored in list at position index.
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index given position
	 * @return Object at given position
	 */
	T get(int index);

	/**
	 * This method inserts (does not overwrite) the given value at the given
	 * position in list. Elements at position and at greater positions must be
	 * shifted one place toward the end so there is space for new element. The legal
	 * positions are 0 to size.
	 * 
	 * 
	 * @param value    that will be inserted
	 * @param position on which element will be inserted
	 */
	void insert(T value, int position);

	/**
	 * Searches the list and returns the index of the first occurrence of the given
	 * value.
	 * 
	 * 
	 * @param value given value
	 * @return index of the first occurrence -1 if value is not in list
	 */
	int indexOf(Object value);

	/**
	 * Removes element at specified index from list. Element that was previously at
	 * location index+1after this operation is on location index, etc. Legal indexes
	 * are 0 to size-1.
	 * 
	 * @param index given index
	 */
	void remove(int index);

}
