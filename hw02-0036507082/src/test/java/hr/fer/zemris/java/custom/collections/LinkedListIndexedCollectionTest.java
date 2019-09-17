package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {
	
	LinkedListIndexedCollection collection;
	
	public void input() {
		collection.add(Integer.valueOf(5));
		collection.add("test");
	}
	
	@BeforeEach
	public void setUp() {
		collection = new LinkedListIndexedCollection();
	}

	@Test
	void testSize() {
		assertEquals(0, collection.size());
		input();
		assertThrows(NullPointerException.class, () -> collection.add(null));
		assertEquals(2, collection.size());
	}

	@Test
	void testAdd() {
		input();
		assertThrows(NullPointerException.class, () -> collection.add(null));
		assertEquals(5, collection.get(0));
		assertEquals("test", collection.get(1));
		assertEquals(2, collection.size());
	}

	@Test
	void testContains() {
		input();
		assertEquals(true, collection.contains("test"));
		assertEquals(false, collection.contains("ab"));
		assertEquals(true, collection.contains(5));
		assertEquals(false, collection.contains(3));
	}

	@Test
	void testRemoveObject() {
		input();
		
		assertEquals(true, collection.contains("test"));
		assertEquals(true, collection.remove("test"));
		assertEquals(false, collection.contains("test"));
		assertEquals(false, collection.remove("test"));
		assertEquals(1, collection.size());
		
		assertEquals(true, collection.contains(5));
		assertEquals(true, collection.remove((Object)5));
		assertEquals(false, collection.contains(5));
		assertEquals(0, collection.size());
	}

	@Test
	void testToArray() {
		input();
		
		Object[] array = collection.toArray();
		
		assertEquals(5, array[0]);
		assertEquals("test", array[1]);
	}

	@Test
	void testForEach() {
		class TestProc extends Processor {
			public int sum = 0;
			@Override
			public void process(Object value) {
				sum += (int) value;
			}
		}
		collection.add(2);
		collection.add(5);
		collection.add(3);
		
		TestProc proc = new TestProc();
		collection.forEach(proc);
		assertEquals(10, proc.sum);
	}

	@Test
	void testClear() {
		input();
		
		assertEquals(true, collection.contains(5));
		collection.clear();
		assertEquals(0, collection.size());
		assertEquals(false, collection.contains(5));
	}

	@Test
	void testLinkedListIndexedCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(0, col.size());
	}

	@Test
	void testLinkedListIndexedCollectionCollection() {
		input();
		LinkedListIndexedCollection col = new LinkedListIndexedCollection(collection);
		assertEquals(true, col.contains(5));
		assertEquals(true, col.contains("test"));
		assertEquals(false, col.contains(7));
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}

	@Test
	void testGet() {
		input();
		assertEquals(5, collection.get(0));
		assertEquals("test", collection.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(5));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
	}

	@Test
	void testInsert() {
		collection.insert(3, 0);
		assertEquals(true, collection.contains(3));
		
		input();
		
		collection.insert(2, 1);
		assertEquals(3, collection.get(0));
		assertEquals(2, collection.get(1));
		assertEquals(5, collection.get(2));
		assertEquals("test", collection.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(7, 8));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(7, -2));
	}

	@Test
	void testIndexOf() {
		input();
		assertEquals(0, collection.indexOf(5));
		assertEquals(1, collection.indexOf("test"));
		assertEquals(-1, collection.indexOf(8));
	}

	@Test
	void testRemoveInt() {
		input();
		collection.remove(0);
		assertEquals(false, collection.contains(5));
		assertEquals("test", collection.get(0));
		assertEquals(1, collection.size());
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(5));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-5));
	}

}
