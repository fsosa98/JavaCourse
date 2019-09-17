package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;
import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

/**
 * Class SimpleHashtableDemo for testing purpose.
 *
 * @author Filip
 */
public class SimpleHashtableDemo {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		System.out.println(examMarks);
		System.out.println();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> it = examMarks.iterator();
		while (it.hasNext()) {
			TableEntry<String, Integer> pair = it.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println();
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}
	}

}
