package searching.slagalica;

import java.util.Arrays;

/**
 * Class KonfiguracijaSlagalice defines data structure and a set of methods for
 * working with it.
 * 
 * Users can index of space and array.
 * 
 * @author Filip
 */
public class KonfiguracijaSlagalice {

	private int[] polje;

	/**
	 * Constructor with given array.
	 * 
	 * @param polje given array
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}

	/**
	 * Getter for array.
	 * 
	 * @return array
	 */
	public int[] getPolje() {
		return polje;
	}

	/**
	 * Getter for index of space.
	 * 
	 * @return index
	 */
	public int indexOfSpace() {
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0)
				return i;
		}
		throw new RuntimeException();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < polje.length; i++) {
			if (i % 3 == 0 && i != 0) {
				sb.append("\n");
			}
			if (polje[i] == 0) {
				sb.append("* ");
				continue;
			}
			sb.append(polje[i] + " ");
		}
		return new String(sb);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}

}
