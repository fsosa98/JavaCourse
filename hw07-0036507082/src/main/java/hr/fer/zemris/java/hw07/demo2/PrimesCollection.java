package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Class PrimesCollection defines data structure and a set of methods for
 * working with it.
 * 
 * Users can get iterator.
 * 
 * @author Filip
 */
public class PrimesCollection implements Iterable<Integer> {

	private int number;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param number
	 */
	public PrimesCollection(int number) {
		this.number = number;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIter(this);
	}

	/**
	 * Class PrimesCollection is an iterator of primes numbers.
	 * 
	 */
	private static class PrimesIter implements Iterator<Integer> {

		private PrimesCollection col;
		private int currentIndex;
		private int currentPrime;

		/**
		 * Constructor with given parameters.
		 * 
		 * @param col
		 */
		private PrimesIter(PrimesCollection col) {
			this.col = col;
			this.currentPrime = 1;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < col.number;
		}

		@Override
		public Integer next() {
			currentPrime++;
			boolean notPrime = true;
			while (notPrime) {
				notPrime = false;
				for (int i = 2; i < currentPrime; i++) {
					if (currentPrime % i == 0) {
						notPrime = true;
						currentPrime++;
						break;
					}
				}
			}
			currentIndex++;
			return currentPrime;
		}

	}

}
