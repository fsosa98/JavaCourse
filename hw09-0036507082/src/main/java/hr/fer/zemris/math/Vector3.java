package hr.fer.zemris.math;

/**
 * Class Vector3 defines 3D vector and a set of methods for working with it.
 * 
 * Users can add, sub, dot, cross, normalize and scale vectors.
 * 
 * @author Filip
 */
public class Vector3 {

	private double x;
	private double y;
	private double z;

	/**
	 * Constructor which initializes vector with given x, y and z.
	 * 
	 * @param x given x
	 * @param y given y
	 * @param z given z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * This method calculates the length of the vector.
	 * 
	 * @return length of vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns normalized vector.
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double d = this.norm();
		return new Vector3(x / d, y / d, z / d);
	}

	/**
	 * This method adds two vectors.
	 * 
	 * @param other given vector
	 * @return new vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * This method subs two vectors.
	 * 
	 * @param other given vector
	 * @return new vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * This method calculates dot product of two vectors.
	 * 
	 * @param other given vector
	 * @return new vector
	 */
	public double dot(Vector3 other) {
		return (this.x * other.x + this.y * other.y + this.z * other.z);
	}

	/**
	 * This method calculates cross product of two vectors.
	 * 
	 * @param other given vector
	 * @return new vector
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x);
	}

	/**
	 * This method creates new vector scaled with given scaler.
	 * 
	 * @param s given scaler
	 * @return new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}

	/**
	 * This method calculates cosAngle between two vectors.
	 * 
	 * @param other given vector
	 * @return cosAngle
	 */
	public double cosAngle(Vector3 other) {
		return (this.dot(other)) / (this.norm() * other.norm());
	}

	/**
	 * Getter for x.
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y.
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for z.
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * This method returns vector in array.
	 * 
	 * @return array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%6f, %6f, %6f)", x, y, z);
	}

}
