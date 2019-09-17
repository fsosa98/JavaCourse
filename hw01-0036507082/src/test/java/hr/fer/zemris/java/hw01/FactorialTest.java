package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {

	@Test
	void test1() {
		assertEquals(24L, Factorial.faktorijel("4"));
	}
	
	@Test
	void test2() {
		assertEquals(2432902008176640000L, Factorial.faktorijel("20"));
	}
	
	@Test
	void test3() {
		try {
			Factorial.faktorijel("-4");
			fail();
		}catch(IllegalArgumentException exception) {
			assertEquals("'-4' nije broj u dozvoljenom rasponu.", exception.getMessage());
		}
	}
	
	@Test
	void test4() {
		try {
			Factorial.faktorijel("štefica");
			fail();
		}catch(IllegalArgumentException exception) {
			assertEquals("'štefica' nije cijeli broj.", exception.getMessage());
		}
	}

}
