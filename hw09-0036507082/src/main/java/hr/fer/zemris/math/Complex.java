package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Complex defines complex number.
 * 
 * Users can get real, imaginary part of complex number, get module and angle
 * complex number, add, subtract, multiply or divide two complex numbers.
 * 
 * @author Filip
 */
public class Complex {

	private double re;
	private double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor
	 */
	public Complex() {
	}

	/**
	 * Constructor initializes complex number with given real and imaginary part.
	 * 
	 * @param re given real part
	 * @param im given real imaginary
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * This method calculates module of complex number.
	 * 
	 * @return module
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * This method multiplies two complex numbers.
	 * 
	 * @param c complex number to multiply.
	 * @return complex number
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Given complex number is null.");
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}

	/**
	 * This method divides two complex numbers.
	 * 
	 * @param c complex number to divide.
	 * @return complex number
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Given complex number is null.");
		Complex num = this.multiply(new Complex(c.re, -c.im));
		double denominator = Math.pow(c.re, 2) + Math.pow(c.im, 2);
		if (Double.compare(denominator, 0.0) == 0) {
			throw new IllegalArgumentException("Nazivnik ne smije biti 0.");
		}
		return new Complex(num.re / denominator, num.im / denominator);
	}

	/**
	 * This method adds two complex numbers.
	 * 
	 * @param c complex number to add.
	 * @return complex number
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Given complex number is null.");
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * This method subs two complex numbers.
	 * 
	 * @param c complex number to sub.
	 * @return complex number
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Given complex number is null.");
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * This method calculates negative complex number.
	 * 
	 * @return complex number
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	/**
	 * This method is used for raising complex number to given exponent.
	 * 
	 * @param n exponent
	 * @return complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent must be greater then 0.");
		}
		double mag = Math.pow(this.module(), n);
		double ang = n * this.getAngle();
		return fromMagnitudeAndAngle(mag, ang);
	}

	/**
	 * This method is used for calculating roots of complex number.
	 * 
	 * @param n root exponent
	 * @return list of complex numbers
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new ArrayList<Complex>();
		if (n <= 0) {
			throw new IllegalArgumentException("Exponent must be greater or equals then 0.");
		}
		double mag = Math.pow(this.module(), 1. / n);
		double ang = this.getAngle();

		for (int i = 0; i < n; i++) {
			list.add(fromMagnitudeAndAngle(mag, (ang + 2 * i * Math.PI) / n));
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(re);
		if (im<0) {
			sb.append("-");
		}else {
			sb.append("+");
		}
		sb.append("i").append(Math.abs(im));
		return new String(sb);
		
	}

	/**
	 * Getter for angle.
	 * 
	 * @return angle
	 */
	private double getAngle() {
		double ang = Math.atan(im / re);
		if (re >= 0 && im < 0) {
			ang += 2 * Math.PI;
		}
		if (re < 0 && im < 0) {
			ang += Math.PI;
		}
		if (re < 0 && im >= 0) {
			ang += Math.PI;
		}
		return ang;
	}

	/**
	 * This method constructs complex number with given magnitude and angle.
	 * 
	 * @param magnitude given magnitude
	 * @param angle     given angle
	 * @return complex number
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

}
