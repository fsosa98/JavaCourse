package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Vector defines vector and a set of methods for working with it.
 * 
 * Users can add values to vector, get dot product of two vector and get
 * magnitude of vector.
 * 
 * @author Filip
 */
public class Vector {

	private List<Double> values;

	/**
	 * Default constructor.
	 */
	public Vector() {
		values = new ArrayList<Double>();
	}

	/**
	 * Method for adding value.
	 * 
	 * @param value
	 */
	public void addValue(Double value) {
		values.add(value);
	}

	/**
	 * Method for getting magnitude of vector.
	 * 
	 * @return magnitude
	 */
	public double mag() {
		double mag = 0;
		for (double val : values) {
			mag += val * val;
		}
		return Math.sqrt(mag);
	}

	/**
	 * Returns dot product of given vectors.
	 * 
	 * @param vi
	 * @param vj
	 * @return
	 */
	public static double dot(Vector vi, Vector vj) {
		double dot = 0;
		for (int i = 0; i < vi.values.size(); i++) {
			dot += vi.values.get(i) * vj.values.get(i);
		}
		return dot;
	}

}
