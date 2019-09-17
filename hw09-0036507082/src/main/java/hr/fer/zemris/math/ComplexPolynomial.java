package hr.fer.zemris.math;

/**
 * Class ComplexPolynomial defines polynomial.
 * 
 * Users can get order of polynomial, multiply two polynomials, derive
 * polynomial and apply value to polynomial
 * 
 * @author Filip
 */
public class ComplexPolynomial {

	private Complex[] factors;

	/**
	 * Constructor initializes polynomial with given parameter.
	 * 
	 * @param factors given factors of polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * This method calculates order of polynomial.
	 * 
	 * @return order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * This method multiplies two polynomials.
	 * 
	 * @param p given polynomial
	 * @return new polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] fac = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < this.factors.length + p.factors.length - 1; i++) {
			fac[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				fac[i + j] = fac[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(fac);
	}

	/**
	 * This method derives polynomial.
	 * 
	 * @return new polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] fac = new Complex[order()];
		for (int i = 1; i < this.factors.length; i++) {
			fac[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(fac);
	}

	/**
	 * This method apply given complex number to polynomial.
	 * 
	 * @param z given complex number
	 * @return complex
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append("(").append(factors[i]).append(")");
			if (i == 0)
				continue;
			sb.append("*z^").append(i).append("+");
		}
		return new String(sb);
	}

}
