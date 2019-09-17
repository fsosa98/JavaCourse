package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {
	
	private final static double DELTA = 10E-6;

	@Test
	void testComplexNumber() {
		ComplexNumber c = new ComplexNumber(2, 3);
		assertEquals(2, c.getReal(), DELTA);
		assertEquals(3, c.getImaginary(), DELTA);
	}

	@Test
	void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(2);
		assertEquals(2, c.getReal(), DELTA);
		assertEquals(0, c.getImaginary(), DELTA);
	}

	@Test
	void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(3);
		assertEquals(0, c.getReal(), DELTA);
		assertEquals(3, c.getImaginary(), DELTA);
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI / 4.);
		assertEquals(1, c.getReal(), DELTA);
		assertEquals(1, c.getImaginary(), DELTA);
	}
	
	@Test
	void testParse() {
		ComplexNumber c = ComplexNumber.parse("351");
		assertEquals(351, c.getReal());
		assertEquals(0, c.getImaginary());
		
		c = ComplexNumber.parse("-3.71");
		assertEquals(-3.71, c.getReal());
		assertEquals(0, c.getImaginary());
		
		c = ComplexNumber.parse("3.51i");
		assertEquals(0, c.getReal());
		assertEquals(3.51, c.getImaginary());
		
		c = ComplexNumber.parse("-317i");
		assertEquals(0, c.getReal());
		assertEquals(-317, c.getImaginary());
		
		c = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(-2.71, c.getReal());
		assertEquals(-3.15, c.getImaginary());
		
		c = ComplexNumber.parse("31+24i");
		assertEquals(31, c.getReal());
		assertEquals(24, c.getImaginary());
		
		c = ComplexNumber.parse("-1-i");
		assertEquals(-1, c.getReal());
		assertEquals(-1, c.getImaginary());
		
		c = ComplexNumber.parse("-1+i");
		assertEquals(-1, c.getReal());
		assertEquals(1, c.getImaginary());
		
		c = ComplexNumber.parse("-i");
		assertEquals(0, c.getReal());
		assertEquals(-1, c.getImaginary());
		
		assertThrows(NullPointerException.class, () -> ComplexNumber.parse(null));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i351"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-i3.17"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("štefica"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse(""));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-+i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-+2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.71+-3.15i"));
	}

	@Test
	void testGetReal() {
		ComplexNumber c = new ComplexNumber(2, 3);
		assertEquals(2, c.getReal(), DELTA);
	}

	@Test
	void testGetImaginary() {
		ComplexNumber c = new ComplexNumber(2, 3);
		assertEquals(3, c.getImaginary(), DELTA);
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(Math.sqrt(2), c.getMagnitude(), DELTA);
	}

	@Test
	void testGetAngle() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(Math.PI / 4., c.getAngle(), DELTA);
	}

	@Test
	void testAdd() {
		ComplexNumber c1 = new ComplexNumber(2, 6);
		ComplexNumber c2 = new ComplexNumber(1, 1);
		ComplexNumber c3 = c1.add(c2);
		assertEquals(3, c3.getReal(), DELTA);
		assertEquals(7, c3.getImaginary(), DELTA);
	}

	@Test
	void testSub() {
		ComplexNumber c1 = new ComplexNumber(2, 6);
		ComplexNumber c2 = new ComplexNumber(1, 1);
		ComplexNumber c3 = c1.sub(c2);
		assertEquals(1, c3.getReal(), DELTA);
		assertEquals(5, c3.getImaginary(), DELTA);
	}

	@Test
	void testMul() {
		ComplexNumber c1 = new ComplexNumber(1, 4);
		ComplexNumber c2 = new ComplexNumber(5, 1);
		ComplexNumber c3 = c1.mul(c2);
		assertEquals(1, c3.getReal(), DELTA);
		assertEquals(21, c3.getImaginary(), DELTA);
	}

	@Test
	void testDiv() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(4, -3);
		ComplexNumber c3 = c1.div(c2);
		assertEquals(6. / 25, c3.getReal(), DELTA);
		assertEquals(17. / 25, c3.getImaginary(), DELTA);
		assertThrows(IllegalArgumentException.class, () -> c1.div(new ComplexNumber(0, 0)));
	}

	@Test
	void testPower() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertThrows(IllegalArgumentException.class, () -> c1.power(-5));
		ComplexNumber c2 = c1.power(10);
		assertEquals(0, c2.getReal(), DELTA);
		assertEquals(32, c2.getImaginary(), DELTA);
	}

	@Test
	void testRoot() {
		ComplexNumber c1 = new ComplexNumber(1, 0);
		ComplexNumber[] c2 = c1.root(6);
		double mag = Math.pow(1, 1. / 1);
		for (ComplexNumber c : c2) {
			assertEquals(mag, c.getMagnitude(), DELTA);
		}
		assertEquals(0, c2[0].getAngle(), DELTA);
		assertEquals(1/3. * Math.PI, c2[1].getAngle(), DELTA);
		assertEquals(2/3. * Math.PI, c2[2].getAngle(), DELTA);
		assertEquals(Math.PI, c2[3].getAngle(), DELTA);
		assertEquals(4/3. * Math.PI, c2[4].getAngle(), DELTA);
		assertEquals(5/3. * Math.PI, c2[5].getAngle(), DELTA);
	}

	@Test
	void testToString() {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		assertEquals("2.0+2.0i", c1.toString());
		c1 = new ComplexNumber(0, 2);
		assertEquals("2.0i", c1.toString());
		c1 = new ComplexNumber(-2, -2);
		assertEquals("-2.0-2.0i", c1.toString());
		c1 = new ComplexNumber(+2, +2);
		assertEquals("2.0+2.0i", c1.toString());
		c1 = new ComplexNumber(0, 0);
		assertEquals("0.0", c1.toString());
	}

}
