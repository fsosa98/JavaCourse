package hr.fer.zemris.java.hw07.demo2;

/**
 * Class PrimesDemo2 is demo class.
 * 
 * @author Filip
 */
public class PrimesDemo2 {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
