package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * Class ComplexNumber defines complex number.
 * 
 * Users can get real, imaginary part of complex number,
 * get magnitude and angle complex number,
 * add, subtract, multiply or divide two complex numbers.
 * 
 * @author Filip
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Constructor initializes complex number with
	 * given real and imaginary part.
	 * 
	 * @param real given real part
	 * @param imaginary given real imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * This method constructs complex number with 
	 * given real part and imaginary part is 0.
	 * 
	 * @param real given real part
	 * @return complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * This method constructs complex number with 
	 * given imaginary part and real part is 0.
	 * 
	 * @param imaginary given real part
	 * @return complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * This method constructs complex number with 
	 * given magnitude and angle.
	 * 
	 * @param magnitude given magnitude
	 * @param angle given angle
	 * @return complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * This method checks if there is illegal argument.
	 * 
	 * @param s given parameter
	 * @param input given parameter
	 * @param i given parameter
	 */
	public static void parseHelper(String s, char[] input, int i) {
		if (input.length > i+1 && i != -1) {
			if (input[i+1] == '+' || input[i+1] == '-') {
				throw new IllegalArgumentException("Unijeli ste krivi izraz.");
			}
		}
	}
	
	/**
	 * This method returns real part of complex number.
	 * 
	 * 
	 * @param s given parameter
	 * @param c given parameter
	 * @return real part of complex number
	 */
	public static double parseReal(String s, char c) {
		try {
			double real = Double.parseDouble(s);
			if (c == '-') {
				real = -real;
			}
			return real;
		}catch(NumberFormatException exc) {
			throw new IllegalArgumentException("Unijeli ste krivi izraz.");
		}
	}

	/**
	 * This method returns real part of complex number.
	 * 
	 * @param s given parameter
	 * @return imaginary part of complex number
	 */
	public static double parseImaginary(String s) {
		try {
			double imaginary;
			String[] parts = s.split("i");
			if (parts.length == 0) {
				imaginary = 1;
			}else {
				imaginary = Double.parseDouble(parts[0]);
			}
			return imaginary;
		}catch(NumberFormatException exc) {
			throw new IllegalArgumentException("Unijeli ste krivi izraz.");
		}
	}
	
	/**
	 * This method is used for parsing complex number from string.
	 * 
	 * @param s given string
	 * @return complex number
	 */
	public static ComplexNumber parse(String s) {
		Objects.requireNonNull(s, "Unijeli ste krivi izraz.");
		String[] parts = s.replace('+', ' ').replace('-', ' ').trim().split(" ");
		char[] input = s.toCharArray();
		
		for (char c : input) {
			if (c != '+' && c!= '-' && c != 'i' && !Character.isDigit(c) && c != '.') {
				throw new IllegalArgumentException("Unijeli ste krivi izraz.");
			}
		}if (s.isEmpty()) {
			throw new IllegalArgumentException("Unijeli ste krivi izraz.");
		}
		
		parseHelper(s, input, s.indexOf('+'));		//check if
		parseHelper(s, input, s.lastIndexOf('+'));	//++, --
		parseHelper(s, input, s.indexOf('-'));		//+- or -+
		parseHelper(s, input, s.lastIndexOf('-'));	//is in string
		
		if (parts.length == 1) {
			if (!parts[0].contains("i")) {
				double real = parseReal(parts[0], input[0]);
				return new ComplexNumber(real, 0);
			}
			else {
				double imaginary = parseImaginary(parts[0]);
				if (input[0] == '-') {
					imaginary = -imaginary;
				}
				return new ComplexNumber(0, imaginary);
			}
		}else if (parts.length == 2){
			double real = parseReal(parts[0], input[0]);
			double imaginary = parseImaginary(parts[1]);
			if (s.lastIndexOf('-') != 0 && s.lastIndexOf('-') != -1) {
				imaginary = -imaginary;
			}
			return new ComplexNumber(real, imaginary);
			
		}
		else {
			throw new IllegalArgumentException("Unijeli ste krivi izraz.");
		}
		
	}

	/**
	 * Getter.
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter.
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Getter.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Getter.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		double ang = Math.atan(imaginary / real);
		if (real >= 0 && imaginary < 0) {
			ang += 2 * Math.PI;
		}if (real < 0 && imaginary < 0) {
			ang += Math.PI;
		}if (real < 0 && imaginary >= 0) {
			ang += Math.PI;
		}
		return ang;
	}
	
	/**
	 * This method adds two complex numbers.
	 * 
	 * @param c complex number to add.
	 * @return complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * This method subs two complex numbers.
	 * 
	 * @param c complex number to sub.
	 * @return complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * This method multiplies two complex numbers.
	 * 
	 * @param c complex number to multiply.
	 * @return complex number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}
	
	/**
	 * This method divides two complex numbers.
	 * 
	 * @param c complex number to divide.
	 * @return complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber num = this.mul(new ComplexNumber(c.real, -c.imaginary));
		double denominator = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		if (Double.compare(denominator, 0.0) == 0) {
			throw new IllegalArgumentException("Nazivnik ne smije biti 0.");
		}
		return new ComplexNumber(num.real / denominator, num.imaginary / denominator);
	}
	
	/**
	 * This method is used for raising complex number to given exponent.
	 * 
	 * @param n exponent
	 * @return complex number
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija mora biti veća ili jednaka 0.");
		}
		double mag = Math.pow(this.getMagnitude(), n);
		double ang = n * this.getAngle();
		return fromMagnitudeAndAngle(mag, ang);	
	}
	
	/**
	 * This method is used for calculating roots of complex number.
	 * 
	 * @param n root exponent
	 * @return complex number
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Potencija mora biti veća ili jednaka 0.");
		}
		ComplexNumber[] numbers = new ComplexNumber[n];
		double mag = Math.pow(this.getMagnitude(), 1. / n);
		double ang = this.getAngle();
		
		for (int i = 0; i < n; i ++) {
			numbers[i] = fromMagnitudeAndAngle(mag, (ang + 2*i*Math.PI) / n);
		}
		return numbers;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String toString() {
		String s = "";
		if (real != 0) {
			s += Double.toString(real);
		}if (imaginary > 0 && real != 0) {
			s += "+" + Double.toString(imaginary) + "i";
		}if ((imaginary > 0 && real == 0) || imaginary < 0) {
			s += Double.toString(imaginary) + "i";
		}if (real == 0 && imaginary == 0) {
			s += "0.0";
		}
		return s;
	}
}
