package hr.fer.zemris.math;

/**
 * Class ComplexRootedPolynomial defines polynomial.
 * 
 * Users can apply some value to polynomial and get ComplexPolynomial.
 * 
 * @author Filip
 */
public class ComplexRootedPolynomial {

	private Complex constant;
	private Complex[] roots;

	/**
	 * Constructor initializes polynomial with given parameters.
	 * 
	 * @param constant given constant
	 * @param roots    given roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * This method apply given complex number to polynomial.
	 * 
	 * @param z given complex number
	 * @return complex
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex c : roots) {
			result = result.multiply(z.sub(c));
		}
		return result;
	}

	/**
	 * This method returns ComplexPolynomial of given polynomial.
	 * 
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);
		for (Complex c : roots) {
			result = result.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(constant).append(")");
		for (int i = 0; i < roots.length; i++) {
			sb.append("*(z-(").append(roots[i]).append("))");
		}
		return new String(sb);
	}

	/**
	 * This method finds index of closest root for given complex number z that is
	 * within treshold, if there is no such root, returns -1
	 * 
	 * @param z        given complex number
	 * @param treshold given treshold
	 * @return index
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		for (int i = 0; i < roots.length; i++) {
			if (z.sub(roots[i]).module() < treshold) {
				index = i;
				treshold = z.sub(roots[i]).module();
			}
		}
		return index;
	}

}
