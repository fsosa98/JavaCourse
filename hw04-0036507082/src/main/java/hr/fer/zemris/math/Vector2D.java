package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class Vector2D defines 2D vector and a set of methods for working with it.
 * 
 * Users can get translate, rotate, scale and copy vector.
 * 
 * @author Filip
 */
public class Vector2D {

	private double x;
	private double y;

	/**
	 * Constructor which initializes vector with given x and y.
	 * 
	 * @param x given x
	 * @param y given y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * This method is used for translating vector with given vector.
	 * 
	 * @param offset given vector
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset, "Offset is null.");
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * This method creates new vector translated with given vector.
	 * 
	 * @param offset given vector
	 * @return translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset, "Offset is null.");
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}

	/**
	 * This method rotates vector with given angle parameter.
	 * 
	 * @param angle given angle
	 */
	public void rotate(double angle) {
		double oldX = x;
		x = Math.cos(angle) * x - Math.sin(angle) * y;
		y = Math.sin(angle) * oldX + Math.cos(angle) * y;
	}

	/**
	 * This method creates new vector rotated with given angle parameter.
	 * 
	 * @param angle given angle
	 * @return new rotated vector
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle) * x - Math.sin(angle) * y, Math.sin(angle) * x + Math.cos(angle) * y);
	}

	/**
	 * This method scales given vector with given scaler.
	 * 
	 * @param scaler given scaler
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * This method creates new vector scaled with given scaler.
	 * 
	 * @param scaler given scaler
	 * @return new scaled vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * This method cop vector.
	 * 
	 * @return new vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
