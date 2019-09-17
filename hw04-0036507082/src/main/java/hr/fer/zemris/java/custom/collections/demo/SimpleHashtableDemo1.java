package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Class SimpleHashtableDemo1 for testing purpose.
 *
 * @author Filip
 */
public class SimpleHashtableDemo1 {

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
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

		// iz kolekcije se uklanja ocjena za Ivanu na korektan način (nema iznimke
//		while(iter.hasNext()) {
//			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
//			if(pair.getKey().equals("Ivana")) {
//				iter.remove();  // sam iterator kontrolirano uklanja trenutni element}}
//			}
//		}
//		System.out.println(examMarks);

		
		//Sljedeći kod bacio bi IllegalStateException jer se uklanjanje poziva više od jednom za trenutni par nad kojim je iterator
//		while(iter.hasNext()) {
//			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
//			if(pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}

		
		//Sljedeći kod bacio bi ConcurrentModificationException jer se uklanjanje poziva “izvana”
//		while(iter.hasNext()) {
//			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
//			if(pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}

		
		//Sljedeći kod trebao bi ispisati sve parove i po završetku ostaviti kolekciju praznom
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//			iter.remove();
//		}
//		System.out.printf("Veličina: %d%n", examMarks.size());
//		System.out.println(examMarks);
	}

}
