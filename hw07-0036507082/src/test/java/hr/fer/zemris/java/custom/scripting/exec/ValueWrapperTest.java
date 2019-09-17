package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	ValueWrapper v1;
	ValueWrapper v2;
	ValueWrapper v3;
	ValueWrapper v4;
	ValueWrapper v5;
	ValueWrapper v6;
	ValueWrapper v7;
	ValueWrapper v8;
	ValueWrapper vv1;
	ValueWrapper vv2;

	@BeforeEach
	void setUp() {
		v1 = new ValueWrapper(null);
		v2 = new ValueWrapper(null);
		v3 = new ValueWrapper("1.2E1");
		v4 = new ValueWrapper(Integer.valueOf(1));
		v5 = new ValueWrapper("12");
		v6 = new ValueWrapper(Integer.valueOf(1));
		v7 = new ValueWrapper("Ankica");
		v8 = new ValueWrapper(Integer.valueOf(1));
		vv1 = new ValueWrapper(Boolean.valueOf(true));
		vv2 = new ValueWrapper(Integer.valueOf(5));
	}

	@Test
	void testAdd() {
		v1.add(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);

		v3.add(v4.getValue());
		assertEquals(v3.getValue(), 13.0);
		assertEquals(v4.getValue(), 1);

		v5.add(v6.getValue());
		assertEquals(v5.getValue(), 13);
		assertEquals(v6.getValue(), 1);

		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
		assertThrows(RuntimeException.class, () -> vv1.add(Integer.valueOf(5)));
		assertThrows(RuntimeException.class, () -> vv2.add(Boolean.valueOf(true)));
	}

	@Test
	void testSubtract() {
		v1.subtract(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);

		v3.subtract(v4.getValue());
		assertEquals(v3.getValue(), 11.0);
		assertEquals(v4.getValue(), 1);

		v5.subtract(v6.getValue());
		assertEquals(v5.getValue(), 11);
		assertEquals(v6.getValue(), 1);

		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
		assertThrows(RuntimeException.class, () -> vv1.subtract(Integer.valueOf(5)));
		assertThrows(RuntimeException.class, () -> vv2.subtract(Boolean.valueOf(true)));
	}

	@Test
	void testMultiply() {
		v1.multiply(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);

		v3.multiply(v4.getValue());
		assertEquals(v3.getValue(), 12.0);
		assertEquals(v4.getValue(), 1);

		v5.multiply(v6.getValue());
		assertEquals(v5.getValue(), 12);
		assertEquals(v6.getValue(), 1);

		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
		assertThrows(RuntimeException.class, () -> vv1.multiply(Integer.valueOf(5)));
		assertThrows(RuntimeException.class, () -> vv2.multiply(Boolean.valueOf(true)));
	}

	@Test
	void testDivide() {

		v3.divide(v4.getValue());
		assertEquals(v3.getValue(), 12.0);
		assertEquals(v4.getValue(), 1);

		v5.divide(v6.getValue());
		assertEquals(v5.getValue(), 12);
		assertEquals(v6.getValue(), 1);

		assertThrows(RuntimeException.class, () -> v7.divide(v8.getValue()));
		assertThrows(RuntimeException.class, () -> vv1.divide(Integer.valueOf(5)));
		assertThrows(RuntimeException.class, () -> vv2.divide(Boolean.valueOf(true)));
	}

	@Test
	void testNumCompare() {
		assertEquals(0, v1.numCompare(v2.getValue()));

		assertEquals(true, v3.numCompare(v4.getValue()) > 0);

		assertEquals(true, v3.numCompare(v4.getValue()) > 0);

		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
		assertThrows(RuntimeException.class, () -> vv1.multiply(Integer.valueOf(5)));
		assertThrows(RuntimeException.class, () -> vv2.multiply(Boolean.valueOf(true)));
	}

}
