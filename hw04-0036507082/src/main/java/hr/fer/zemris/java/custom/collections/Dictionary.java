package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class Dictionary defines data structure and a set of methods for working with
 * it.
 * 
 * Users can get size of dictionary, put elements, get elements, check if
 * dictionary is empty or clear dictionary.
 * 
 * @author Filip
 */
public class Dictionary<K, V> {

	private ArrayIndexedCollection<Entry> col;

	/**
	 * Default constructor.
	 * 
	 */
	public Dictionary() {
		col = new ArrayIndexedCollection<>();
	}

	/**
	 * Class Entry defines data structure. Class contains key and value.
	 *
	 */
	private class Entry {
		private K key;
		private V value;

		/**
		 * Constructor which initializes Entry with given key and value.
		 * 
		 * @param key   given key
		 * @param value given value
		 */
		public Entry(K key, V value) {
			Objects.requireNonNull(key, "Key is null.");

			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		private Dictionary<K,V> getOuterType() {
			return Dictionary.this;
		}

	}

	/**
	 * This method checks if dictionary is empty.
	 * 
	 * @return true if dictionary is empty false if dictionary is not empty
	 */
	boolean isEmpty() {
		return col.isEmpty();
	}

	/**
	 * This method calculates the size of collection.
	 * 
	 * @return number of currently stored objects in this dictionary.
	 */
	int size() {
		return col.size();
	}

	/**
	 * This method removes all elements from this dictionary.
	 * 
	 */
	void clear() {
		col.clear();
	}

	/**
	 * This method puts the given key and value into this dictionary.
	 * 
	 * @param key   given key
	 * @param value given value
	 */
	void put(K key, V value) {
		Objects.requireNonNull(key, "Key is null.");
		V val = get(key);
		if (val != null) {
			col.remove(new Entry(key, val));
		}
		col.add(new Entry(key, value));
	}

	/**
	 * This method returns the value that is stored in dictionary with given key.
	 * 
	 * @param key given key
	 * @return value
	 */
	V get(Object key) {
		ElementsGetter<Entry> it = col.createElementsGetter();
		while (it.hasNextElement()) {
			Entry entry = it.getNextElement();
			if ((entry.key).equals(key)) {
				return entry.value;
			}
		}
		return null;
	}

}
