package hr.fer.zemris.java.gui.charts;

/**
 * Class XYValue defines value.
 * 
 * Users can get x and y.
 * 
 * @author Filip
 */
public class XYValue {

	private int x;
	private int y;

	/**
	 * Constructor initializes XYValue parameters.
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
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
