package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vector2DTest {
	
	Vector2D v;
	private final static double DELTA = 10E-6;
	
	@BeforeEach
	void setUp() {
		v = new Vector2D(2, 3);
	}

	@Test
	void testVector2DGetXGetY() {
		assertEquals(2, v.getX());
		assertEquals(3, v.getY());
		
		Vector2D v2 = new Vector2D(2.123, 3.321);
		assertEquals(2.123, v2.getX());
		assertEquals(3.321, v2.getY());
	}

	@Test
	void testTranslate() {
		v.translate(new Vector2D(1, 2));
		assertEquals(3, v.getX());
		assertEquals(5, v.getY());
	}

	@Test
	void testTranslated() {
		Vector2D v2 = v.translated(new Vector2D(1,2));
		assertEquals(3, v2.getX());
		assertEquals(5, v2.getY());
	}

	@Test
	void testRotate() {
		v.rotate(2 * Math.PI);
		assertEquals(2, v.getX(), DELTA);
		assertEquals(3, v.getY(), DELTA);
		
		v.rotate(151 * Math.PI);
		assertEquals(-2, v.getX(), DELTA);
		assertEquals(-3, v.getY(), DELTA);
		
		v.rotate(Math.PI / 2);
		assertEquals(3, v.getX(), DELTA);
		assertEquals(-2, v.getY(), DELTA);
	}

	@Test
	void testRotated() {
		Vector2D v2 = v.rotated(2 * Math.PI);
		assertEquals(2, v2.getX(), DELTA);
		assertEquals(3, v2.getY(), DELTA);
		
		v2 = v.rotated(151 * Math.PI);
		assertEquals(-2, v2.getX(), DELTA);
		assertEquals(-3, v2.getY(), DELTA);
		
		v2 = v.rotated(Math.PI / 2);
		assertEquals(-3, v2.getX(), DELTA);
		assertEquals(2, v2.getY(), DELTA);
	}

	@Test
	void testScale() {
		v.scale(2);
		assertEquals(4, v.getX(), DELTA);
		assertEquals(6, v.getY(), DELTA);
		
		v.scale(2);
		assertEquals(8, v.getX(), DELTA);
		assertEquals(12, v.getY(), DELTA);

		v.scale(-0.5);
		assertEquals(-4, v.getX(), DELTA);
		assertEquals(-6, v.getY(), DELTA);
	}

	@Test
	void testScaled() {
		Vector2D v2 = v.scaled(2);
		assertEquals(4, v2.getX(), DELTA);
		assertEquals(6, v2.getY(), DELTA);
		
		v2 = v2.scaled(2);
		assertEquals(8, v2.getX(), DELTA);
		assertEquals(12, v2.getY(), DELTA);
		
		v2 = v2.scaled(-0.5);
		assertEquals(-4, v2.getX(), DELTA);
		assertEquals(-6, v2.getY(), DELTA);
	}

	@Test
	void testCopy() {
		Vector2D v2 = v.copy();
		assertEquals(2, v2.getX());
		assertEquals(3, v2.getY());
	}

}
