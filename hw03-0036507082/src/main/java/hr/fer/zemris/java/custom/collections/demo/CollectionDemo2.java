package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * Class CollectionDemo2 for testing purpose.
 *
 * @author Filip
 */
public class CollectionDemo2 {

	/**
	 * This is main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());

		Collection col2 = new LinkedListIndexedCollection();
		col2.add("Ivo");
		col2.add("Ana");
		col2.add("Jasna");

		ElementsGetter getter2 = col2.createElementsGetter();
		getter2.getNextElement();

		getter2.processRemaining(System.out::println);
	}

}
