package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHextobyte() {
		byte[] array = Util.hextobyte("01aE22");
		assertEquals(3, array.length);
		assertEquals(1, array[0]);
		assertEquals(-82, array[1]);
		assertEquals(34, array[2]);

		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01a5E22"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aČ22"));
	}

	@Test
	void testBytetohex() {
		String text = Util.bytetohex(new byte[] { 1, -82, 34 });
		assertEquals(6, text.length());
		assertEquals("01ae22", text);
	}

}
