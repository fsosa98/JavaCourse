package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void testFilterTrue1() {
		QueryParser qp1 = new QueryParser("jmbag = \"0000000003\"");
		QueryFilter qf1 = new QueryFilter(qp1.getQuery()); 
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		
		assertEquals(true, qf1.accepts(record));
	}
	
	@Test
	void testFilterFalse1() {
		QueryParser qp1 = new QueryParser("jmbag = \"0000000003\"");
		QueryFilter qf1 = new QueryFilter(qp1.getQuery()); 
		StudentRecord record = new StudentRecord("0000000010", "Dokleja", "Luka", "3");
		
		assertEquals(false, qf1.accepts(record));
	}
	
	@Test
	void testFilterTrue2() {
		QueryParser qp2 = new QueryParser("lastName LIKE \"Bo*\" and firstName>\"J\"");
		QueryFilter qf2 = new QueryFilter(qp2.getQuery()); 
		StudentRecord record = new StudentRecord("0000000004", "Božić", "Marin", "5");
		
		assertEquals(true, qf2.accepts(record));
	}
	
	@Test
	void testFilterFalse2() {
		QueryParser qp2 = new QueryParser("lastName LIKE \"Bo*\" and firstName>\"J\"");
		QueryFilter qf2 = new QueryFilter(qp2.getQuery()); 
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		
		assertEquals(false, qf2.accepts(record));
	}

}
