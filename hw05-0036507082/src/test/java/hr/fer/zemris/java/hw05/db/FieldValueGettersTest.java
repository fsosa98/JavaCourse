package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void testFieldValueGetters() {
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
	}

}
