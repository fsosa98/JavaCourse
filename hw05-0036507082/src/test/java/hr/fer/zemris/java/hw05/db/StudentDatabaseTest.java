package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void testForJMBAG() {
		List<String> list = new ArrayList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	3");
		list.add("0000000003	Bosnić	Andrea	4");
		list.add("0000000004	Božić	Marin	5");
		list.add("0000000005	Brezović	Jusufadis	2");
		list.add("0000000006	Cvrlje	Ivan	3");
		StudentDatabase database = new StudentDatabase(list);
		List<StudentRecord> records = database.filter(s -> true);
		assertEquals(6, records.size());
		list.add("0000000001	Ivić	Ivo		2");
		database = new StudentDatabase(list);
		records = database.filter(s -> true);
		assertEquals(6, records.size());
		list.add("0000000001	Ivić	Ivo		7");
		database = new StudentDatabase(list);
		records = database.filter(s -> true);
		assertEquals(6, records.size());
		
		records = database.filter(s -> false);
		assertEquals(0, records.size());
		
	}

}
