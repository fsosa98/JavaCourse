package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Class EvenIntegerTester defines a single method which can be used for testing
 * given Object.
 * 
 * @author Filip
 */
class EvenIntegerTester implements Tester {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

}
