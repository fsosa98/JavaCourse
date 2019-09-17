package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	ArrayIndexedCollection collection;
	
	public void input() {
		collection.add(Integer.valueOf(5));
		collection.add("test");
	}
	
	@BeforeEach
	public void setUp() {
		collection = new ArrayIndexedCollection();
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
		assertEquals(true, collection.remove(Integer.valueOf(5)));
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
	void testArrayIndexedCollection() {
		ArrayIndexedCollection collectionTest = new ArrayIndexedCollection();
		assertEquals(16, collectionTest.getCapacity());
		assertEquals(0, collectionTest.size());
	}

	@Test
	void testArrayIndexedCollectionInt() {
		ArrayIndexedCollection collectionTest = new ArrayIndexedCollection(7);
		assertEquals(7, collectionTest.getCapacity());
		assertEquals(0, collectionTest.size());
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-2));
	}

	@Test
	void testArrayIndexedCollectionCollection() {
		input();
		
		ArrayIndexedCollection collectionTest = new ArrayIndexedCollection(collection);
		assertEquals(16, collectionTest.getCapacity());
		assertEquals(2, collectionTest.size());
		assertEquals(true, collectionTest.contains("test"));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}

	@Test
	void testArrayIndexedCollectionCollectionInt() {
		input();
		
		ArrayIndexedCollection collectionTest = new ArrayIndexedCollection(collection,18);
		assertEquals(18, collectionTest.getCapacity());
		assertEquals(2, collectionTest.size());
		assertEquals(true, collectionTest.contains("test"));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null,7));
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
