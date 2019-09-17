package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class ArrayIndexedCollection defines data structure and a set of methods for
 * working with it. Class uses array for storing elements.
 * 
 * Users can get size of collection, add elements, check if some element is in
 * collection, remove element, check if collection is empty or clear collection,
 * get element on index, insert elements, get index of some element.
 * 
 * @author Filip
 */
public class ArrayIndexedCollection implements List {

	private int size;
	private Object[] elements;
	private int capacity;
	private static final int DEFAULT_CAPACITY = 16;

	private long modificationCount = 0;

	/**
	 * This method return capacity of collection.
	 * 
	 * @return capacity of collection.
	 */
	protected int getCapacity() {
		return capacity;
	}

	/**
	 * Default constructor. Initialize collection with default capacity.
	 * 
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which initializes collection with given capacity.
	 * 
	 * @param initialCapacity initial capacity.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Kapcitet mora biti veći ili jednak 1.");
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * Constructor which adds elements of given collection to collection and
	 * initializes collection with default capacity.
	 * 
	 * @param other given collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which adds elements of given collection to collection and
	 * initializes collection with given capacity.
	 * 
	 * @param other           given collection
	 * @param initialCapacity given capacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		Objects.requireNonNull(other, "Predana kolekcija ne smije biti null.");

		if (initialCapacity < other.size()) {
			capacity = other.size();
		} else {
			capacity = initialCapacity;
		}

		elements = new Object[capacity];
		addAll(other);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value, "Predani element ne smije biti null.");

		if (size == capacity) {
			elements = Arrays.copyOf(elements, size * 2);
			capacity = size * 2;
		}

		elements[size++] = value;
		modificationCount++;
	}

	/**
	 * This method returns the object that is stored in array at position index.
	 * Valid indexes are 0 to size-1. O(1)
	 * 
	 * @param index given position
	 * @return Object at given position
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index nije u dozvoljenom rasponu.");
		}
		return elements[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * This method inserts (does not overwrite) the given value at the given
	 * position in array. Elements at position and at greater positions must be
	 * shifted one place toward the end so there is space for new element. The legal
	 * positions are 0 to size. O(n)
	 * 
	 * @param value    that will be inserted
	 * @param position on which element will be inserted
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "Predani element ne smije biti null.");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Pozicija nije u dozvoljenom rasponu.");
		}

		if (size == capacity) {
			elements = Arrays.copyOf(elements, size * 2);
			capacity = size * 2;
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
		modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value. O(n)
	 * 
	 * @param value given value
	 * @return index of the first occurrence -1 if value is not in collection
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1after this operation is on location index, etc.
	 * Legal indexes are 0 to size-1.
	 * 
	 * @param index given index
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index nije u dozvoljenom rasponu.");
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[--size] = null;
		modificationCount++;
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
		Object[] elementsNew = Arrays.copyOf(elements, size);
		return elementsNew;
	}

	/**
	 * Class ArrayGetter is an iterator over a ArrayIndexedCollection.
	 * 
	 * Users can check if next element is available, get next element.
	 *
	 */
	private static class ArrayGetter implements ElementsGetter {
		private int ind;
		private ArrayIndexedCollection col;
		private long savedModificationCount;

		/**
		 * Constructor.
		 * 
		 * @param col for iteration.
		 */
		protected ArrayGetter(ArrayIndexedCollection col) {
			ind = 0;
			this.col = col;
			savedModificationCount = col.modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je strukturno mijenjana.");
			}
			return ind < col.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getNextElement() {
			if (savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je strukturno mijenjana.");
			}
			if (ind >= col.size()) {
				throw new NoSuchElementException("Više nema neisporučenih elemenata.");
			} else {
				return col.elements[ind++];
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayGetter(this);
	}
}
