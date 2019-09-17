package hr.fer.zemris.java.hw07.demo2;

/**
 * Class PrimesDemo1 is demo class.
 * 
 * @author Filip
 */
public class PrimesDemo1 {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
