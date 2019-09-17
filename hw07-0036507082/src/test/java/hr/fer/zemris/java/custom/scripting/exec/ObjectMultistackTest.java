package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);

		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);

		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(200.51, multistack.peek("price").getValue());

		assertThrows(NullPointerException.class, () -> multistack.push(null, price));
		assertThrows(NullPointerException.class, () -> multistack.push(null, null));
		assertThrows(NullPointerException.class, () -> multistack.push("year", null));
	}

	@Test
	void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper year1 = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year1);
		ValueWrapper year2 = new ValueWrapper(Integer.valueOf(2019));
		multistack.push("year", year2);

		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);

		assertEquals(2019, multistack.pop("year").getValue());
		assertEquals(200.51, multistack.pop("price").getValue());

		assertEquals(2000, multistack.pop("year").getValue());

		assertThrows(EmptyStackException.class, () -> multistack.pop("year"));
	}

	@Test
	void testPeek() {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper year1 = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year1);
		ValueWrapper year2 = new ValueWrapper(Integer.valueOf(2019));
		multistack.push("year", year2);

		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);

		assertEquals(2019, multistack.peek("year").getValue());
		assertEquals(200.51, multistack.peek("price").getValue());

		multistack.pop("year");
		assertEquals(2000, multistack.peek("year").getValue());

		multistack.pop("year");
		assertThrows(EmptyStackException.class, () -> multistack.peek("year"));
	}

	@Test
	void testIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);

		assertEquals(true, multistack.isEmpty("a"));
		assertEquals(false, multistack.isEmpty("year"));
	}

}
