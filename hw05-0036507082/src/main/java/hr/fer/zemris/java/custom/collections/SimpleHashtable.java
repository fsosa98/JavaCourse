package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class SimpleHashtable defines data structure and a set of methods for working
 * with it.
 * 
 * Users can get size of table, put elements (key + value), get element with
 * given key, check if some element is in table, remove element with given key,
 * check if table is empty or clear table.
 * 
 * @author Filip
 * 
 * @param <K> key
 * @param <V> value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	public TableEntry<K, V>[] table;
	private int size;
	private long modificationCount = 0;
	private static final int DEFAULT_CAPACITY = 16;
	private static final double OVERFLOW = 0.75;

	/**
	 * Default constructor. Initialize table with default capacity.
	 * 
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor. Initialize table with given capacity.
	 * 
	 * @param capacity given capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Number must be grater than 1.");

		capacity = (int) Math.pow(2, Math.ceil(Math.log10(capacity) / Math.log10(2)));
		table = (TableEntry<K, V>[]) new TableEntry[capacity];

	}

	/**
	 * This method puts the given key and value into this table.
	 * 
	 * @param key   given key
	 * @param value given value
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Key is null.");

		if (!putHelper(key, value, table))
			return;

		size++;
		modificationCount++;

		if (size >= OVERFLOW * table.length) {
			doubleTable();
		}
	}

	/**
	 * This is helper method for putting elements in table.
	 * 
	 * @param key   given key
	 * @param value given value
	 * @param table given table
	 * @return true if new element is added false if value is changed
	 */
	public boolean putHelper(K key, V value, TableEntry<K, V>[] table) {
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> node = table[slot];
		TableEntry<K, V> before = null;
		while (node != null) {
			if ((node.key).equals(key)) {
				node.value = value;
				return false;
			}
			before = node;
			node = node.next;
		}
		if (before == null) {
			table[slot] = new TableEntry<>(key, value, null);
		} else {
			before.next = new TableEntry<>(key, value, null);
		}
		return true;
	}

	/**
	 * This method returns the value that is stored in table with given key.
	 * 
	 * @param key given key
	 * @return value with given key
	 */
	public V get(Object key) {
		if (key == null)
			return null;
		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> node = table[slot];
		while (node != null) {
			if ((node.key).equals(key)) {
				return node.value;
			}
			node = node.next;
		}
		return null;
	}

	/**
	 * This method calculates the size of table.
	 * 
	 * @return number of currently stored elements in this table.
	 */
	public int size() {
		return size;
	}

	/**
	 * This method checks if given key is in table.
	 * 
	 * @param key given key
	 * @return true if key is in table false if key is not in table
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;
		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> node = table[slot];
		while (node != null) {
			if ((node.key).equals(key)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}

	/**
	 * This method checks if given value is in table.
	 * 
	 * @param key given value
	 * @return true if value is in table false if value is not in table
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				if (value == null) {
					if (node.value == null) {
						return true;
					}
				} else if ((node.value).equals(value)) {
					return true;
				}
				node = node.next;
			}
		}
		return false;
	}

	/**
	 * This method remove the given value if table contains it.
	 * 
	 * @param key to be removed
	 */
	public void remove(Object key) {
		if (key == null)
			return;
		if (!containsKey(key)) {
			return;
		}

		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> node = table[slot];
		TableEntry<K, V> before = null;
		while (node != null) {
			if ((node.key).equals(key)) {
				if (before == null) {
					table[slot] = node.next;
				} else {
					before.next = node.next;
				}
			}
			before = node;
			node = node.next;
		}
		size--;
		modificationCount++;
	}

	/**
	 * This method checks if table is empty.
	 * 
	 * @return true if table is empty false if table is not empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				if (!first) {
					sb.append(", ");
				} else
					first = false;
				sb.append(node.key).append("=").append(node.value);
				node = node.next;
			}
		}
		sb.append("]");
		return new String(sb);
	}

	/**
	 * This method removes all elements from this table.
	 * 
	 */
	public void clear() {
		clearHelper();
		size = 0;
	}

	/**
	 * Helper method for clear.
	 * 
	 */
	public void clearHelper() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	/**
	 * This method doubles size of table.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doubleTable() {
		TableEntry<K, V>[] doubleTable = (TableEntry<K, V>[]) new TableEntry[2 * table.length];
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				putHelper(node.key, node.value, doubleTable);
				node = node.next;
			}
		}
		clearHelper();
		table = doubleTable;
	}

	/**
	 * Class TableEntry represents one element in table.
	 * 
	 * @author Filip
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K, V> {

		private K key;
		private V value;
		private TableEntry<K, V> next;

		/**
		 * Constructor. Initialize TableEntry with given key, value and next TableEntry.
		 * 
		 * @param key   given key
		 * @param value given value
		 * @param next  given TableEntry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			Objects.requireNonNull(key, "Key is null.");

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * This method return value of TableEntry.
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * This method set value of TableEntry with given value.
		 * 
		 * @param value given value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * This method return key of TableEntry.
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}

	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class IteratorImpl represents Iterator for table.
	 * 
	 * @author Filip
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private int slot;
		private TableEntry<K, V> node;
		private TableEntry<K, V> before;
		private boolean rem;
		private long savedModificationCount;

		/**
		 * Default constructor. Initialize IteratorImpl.
		 * 
		 */
		private IteratorImpl() {
			slot = 0;
			node = table[0];
			rem = false;
			savedModificationCount = SimpleHashtable.this.modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je strukturno mijenjana.");
			}
			if (node != null)
				return true;
			slot++;
			for (; slot < table.length; slot++) {
				if (node != null)
					return true;
				if (node == null) {
					node = table[slot];
					if (node != null)
						return true;
				}
			}
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je strukturno mijenjana.");
			}
			before = node;
			if (hasNext()) {
				node = node.next;
			} else {
				throw new NoSuchElementException();
			}
			rem = true;
			return before;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			if (rem) {
				SimpleHashtable.this.remove(before.key);
				savedModificationCount++;
				rem = false;
			} else {
				throw new IllegalStateException("The next method has not yet been called.");
			}
		}
	}
}
