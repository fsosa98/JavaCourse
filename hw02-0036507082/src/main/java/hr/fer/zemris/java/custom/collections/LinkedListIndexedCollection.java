package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class LinkedListIndexedCollection defines data structure and a set of 
 * methods for working with it. Class uses linked list of nodes for storing elements.
 * 
 * Users can get size of collection, add elements, check if 
 * some element is in collection, remove element, check if 
 * collection is empty or clear collection, get element on index,
 * insert elements, get index of some element.
 * 
 * @author Filip
 */
public class LinkedListIndexedCollection extends Collection {
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Class ListNode represents list node. 
	 * It is a basic building element of this collection.
	 *
	 */
	private static class ListNode {
		
		private Object value;
		private ListNode previous;
		private ListNode next;
		
	}
	
	/**
	 * Default constructor.
	 * 
	 */
	public LinkedListIndexedCollection() {
	}
	
	/**
	 * Constructor which adds elements of given collection to collection.
	 * 
	 * @param other given collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		Objects.requireNonNull(other, "Predana kolekcija ne smije biti null.");
		
		addAll(other);
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value, "Predani element ne smije biti null.");
		
		ListNode element = new ListNode();
		element.value = value;
		element.next = null;
		
		if (first == null) {
			first = element;
		}
		else {
			last.next = element;
			element.previous = last;
		}
		
		last = element;
		size ++;
		
	}
	
	/**
	 * This method returns the ListNode that is stored in 
	 * linked list at position index.
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index given position
	 * @return ListNode at given position
	 */
	public ListNode getNode(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index nije u dozvoljenom rasponu.");
		}
		
		ListNode element;
		
		if (index < size / 2) {
			element = first;
			for (int i = 0; i < size / 2; i ++) {
				if (index == i) {
					break;
				}
				element = element.next;
			}
		}
		else {
			element = last;
			for (int i = size - 1; i >= size / 2; i --) {
				if (index == i) {
					break;
				}
				element = element.previous;
			}
		}
		return element;
	}
	
	/**
	 * This method returns the object that is stored in 
	 * linked list at position index.
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index given position
	 * @return Object at given position
	 */
	public Object get(int index) {
		ListNode element = getNode(index);
		return element.value;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * This method inserts (does not overwrite) the given value 
	 * at the given position in array. Elements at position and at greater positions 
	 * must be shifted one place toward the end so there is space for new element.
	 * The legal positions are 0 to size.
	 * O(n)
	 * 
	 * @param value that will be inserted
	 * @param position on which element will be inserted
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Pozicija nije u dozvoljenom rasponu.");
		}
		
		ListNode element;
		ListNode newElement = new ListNode();
		newElement.value = value;
		
		if (size == position) {
			if (size == 0) {
				first = newElement;
			}
			else {
				last.next = newElement;
				newElement.previous = last;
			}
			last = newElement;
		}
		else {
			element = getNode(position);
			if (element.previous != null) {
				(element.previous).next = newElement;
			}
			newElement.previous = element.previous;
			newElement.next = element;
			element.previous = newElement;
			if (position == 0) {
				first = newElement;
			}
		}
		
		size ++;

	}
	
	/**
	 * Searches the collection and returns the index of 
	 * the first occurrence of the given value.
	 * O(n)
	 * 
	 * @param value given value
	 * @return index of the first occurrence
	 *         -1 if value is not in collection
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		
		ListNode element = first;
		
		for (int i = 0; i < size; i ++) {
			if (value.equals(element.value)) {
				return i;
			}
			element = element.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. 
	 * Element that was previously at location index+1after 
	 * this operation is on location index, etc. 
	 * Legal indexes are 0 to size-1.
	 * 
	 * @param index given index
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index nije u dozvoljenom rasponu.");
		}
		
		if (index == 0) {
			first = first.next;
			if (first != null) {
				first.previous = null;
			}
			if (size == 1) {
				last = null;
			}
		}
		else {
			ListNode element = getNode(index);
			(element.previous).next = element.next;
			if (index == size - 1) {
				last = element.previous;
			}
			else {
				(element.next).previous = element.previous;
			}
		}
		
		size --;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public int size() {
		return size;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index != -1) {
			remove(index);
			return true;
		}
		return false;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public Object[] toArray() {
		Object[] elementsNew = new Object[size];
		ListNode element = first;
		
		for (int i = 0; i < size; i ++) {
			elementsNew[i] = element.value;
			element = element.next;
		}
		return elementsNew;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
    public void forEach(Processor processor) {
		
		ListNode element = first;
		
		for (int i = 0; i < size; i ++) {
			processor.process(element.value);
			element = element.next;
		}
	}
	
}
