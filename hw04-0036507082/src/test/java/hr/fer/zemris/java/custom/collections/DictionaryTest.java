package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {
	
	Dictionary<String, Integer> dictionary;
	
	@BeforeEach
	void setUp() {
		dictionary = new Dictionary<>();
	}

	@Test
	void testIsEmpty() {
		assertEquals(true, dictionary.isEmpty());
		dictionary.put("A", 5);
		assertEquals(false, dictionary.isEmpty());
		dictionary.put("B", 7);
		assertEquals(false, dictionary.isEmpty());
		dictionary.clear();
		assertEquals(true, dictionary.isEmpty());
	}

	@Test
	void testSizeAndClear() {
		assertEquals(0, dictionary.size());
		dictionary.put("A", 5);
		dictionary.put("B", 7);
		dictionary.put("C", 3);
		assertEquals(3, dictionary.size());
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}

	@Test
	void testPut() {
		assertEquals(0, dictionary.size());
		dictionary.put("A", 5);
		assertEquals(1, dictionary.size());
		dictionary.put("B", 7);
		assertEquals(2, dictionary.size());
		dictionary.put("B", 7);
		assertEquals(2, dictionary.size());
		dictionary.put("B", 3);
		assertEquals(2, dictionary.size());
		dictionary.put("A", 3);
		assertEquals(2, dictionary.size());
		dictionary.clear();
		assertEquals(0, dictionary.size());
		assertThrows(NullPointerException.class, () -> dictionary.put(null, 3));
	}

	@Test
	void testGet() {
		assertEquals(null, dictionary.get("A"));
		dictionary.put("A", 5);
		dictionary.put("B", 7);
		assertEquals(5, dictionary.get("A"));
		assertEquals(7, dictionary.get("B"));
		dictionary.put("B", 3);
		assertEquals(3, dictionary.get("B"));
		assertEquals(null, dictionary.get("C"));
	}

}
