package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

import org.junit.jupiter.api.Test;

class UniqueNumbersTest {

	@Test
	void test1() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		assertEquals(42, glava.value);
		assertEquals(21, glava.left.value);
		assertEquals(35, glava.left.right.value);
		assertEquals(76, glava.right.value);
	}
	
	@Test
	void test2() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		assertEquals(4, treeSize(glava));
	}
	
	@Test
	void test3() {
		TreeNode glava = null;
		assertEquals(0, treeSize(glava));
	}
	
	@Test
	void test4() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		assertTrue(containsValue(glava, 42));
		assertFalse(containsValue(glava,5));
	}

}
