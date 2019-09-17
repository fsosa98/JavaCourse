package hr.fer.zemris.java.gui.charts;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class BarChart defines bar chart.
 * 
 * Users can get list of values, name of x and y axis, minimum and maximum y,
 * space value.
 * 
 * @author Filip
 */
public class BarChart {

	List<XYValue> values;
	String xAxis;
	String yAxis;
	int yMin;
	int yMax;
	int space;

	/**
	 * Constructor initializes BarChart parameters.
	 * 
	 * @param values given values
	 * @param xAxis  given x axis
	 * @param yAxis  given y axis
	 * @param yMin   given yMin
	 * @param yMax   given yMax
	 * @param space  given space value
	 */
	public BarChart(List<XYValue> values, String xAxis, String yAxis, int yMin, int yMax, int space) {
		Objects.requireNonNull(values);
		Objects.requireNonNull(xAxis);
		Objects.requireNonNull(yAxis);
		if (yMin < 0 || yMin >= yMax) {
			throw new IllegalArgumentException();
		}
		checkValues(values, yMin);
		Collections.sort(values, (a, b) -> a.getX() - b.getX());
		this.values = values;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.yMin = yMin;
		this.yMax = yMax;
		while ((yMax - yMin) % space != 0) {
			yMax++;
		}
		this.space = space;
	}

	/**
	 * Helper method for checking values.
	 * 
	 * @param values
	 * @param yMin
	 */
	private void checkValues(List<XYValue> values, int yMin) {
		for (XYValue xy : values) {
			if (xy.getY() < yMin)
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Getter for values.
	 * 
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for x axis.
	 * 
	 * @return x axis
	 */
	public String getxAxis() {
		return xAxis;
	}

	/**
	 * Getter for y axis.
	 * 
	 * @return y axis
	 */
	public String getyAxis() {
		return yAxis;
	}

	/**
	 * Getter for y min.
	 * 
	 * @return y min
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter for y max.
	 * 
	 * @return y max
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter for space.
	 * 
	 * @return space
	 */
	public int getSpace() {
		return space;
	}

}
