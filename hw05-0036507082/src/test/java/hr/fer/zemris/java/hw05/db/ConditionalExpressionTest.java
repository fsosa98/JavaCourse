package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void testConditionalExpression() {
		
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,  
				"Bos*",  
				ComparisonOperators.LIKE
		);
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),  // returns lastName from given record
				expr.getStringLiteral()             // returns "Bos*"
		);
		assertEquals(false, recordSatisfies);
		
		record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),  // returns lastName from given record
				expr.getStringLiteral()             // returns "Bos*"
		);
		assertEquals(true, recordSatisfies);
	}

}
