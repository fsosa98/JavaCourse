package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;

class LSystemBuilderImplTest {

	@Test
	void testGenerate() {
		LSystemBuilder builderImpl = new LSystemBuilderImpl().configureFromText(new String[] {"axiom F","production F F+F--F+F"});
		LSystem system = builderImpl.build();
		assertEquals("F", system.generate(0));
		assertEquals("F+F--F+F", system.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}

}
