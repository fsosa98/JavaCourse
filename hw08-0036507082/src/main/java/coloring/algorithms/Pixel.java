package coloring.algorithms;

import java.util.Objects;

/**
 * Class Coloring defines data structure and a set of methods for working with
 * it.
 * 
 * Users can get x and y.
 * 
 * @author Filip
 */
public class Pixel {

	private int x, y;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param x given x
	 * @param y given y
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Getter for x.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
