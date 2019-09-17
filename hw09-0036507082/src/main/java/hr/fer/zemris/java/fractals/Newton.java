package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class Newton is demo class for fractals derived from Newton-Raphson
 * iteration.
 * 
 * @author Filip
 */
public class Newton {

	/**
	 * This is main method
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<Complex>();

		System.out.println(
				"Welcome to Newton-Raphson iteration-based fractal viewer.Please enter at least two roots, one root per line. Enter 'done' when done.");
		String root;
		System.out.print("Root 1> ");
		int i = 1;
		while (!(root = sc.nextLine()).equals("done")) {
			i++;
			root = root.trim();
			try {
				roots.add(parse(root));
			} catch (Exception e) {
				System.out.println("Invalid complex number.");
			}
			System.out.print("Root " + i + "> ");
		}
		if (i <= 2) {
			System.out.println("Please enter at least two roots.");
			System.exit(1);
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE,
				roots.toArray(new Complex[] {}));
		FractalViewer.show(new NewtonProducer(rootedPolynomial));
		sc.close();
	}

	/**
	 * This method is used for parsing complex number from string.
	 * 
	 * @param text given string
	 * @return complex number
	 */
	private static Complex parse(String text) {
		text = text.replace(" ", "");
		char[] data = text.toCharArray();
		int index = 0;

		StringBuilder sb = new StringBuilder();

		// real
		while (true) {
			if (index >= data.length)
				break;
			if (index != 0 && (data[index] == '-' || data[index] == '+')) {
				break;
			}
			if (Character.isDigit(data[index])) {
				sb.append(data[index++]);
				continue;
			}
			if (index == 0 && (data[index] == '-' || data[index] == '+')
					&& (index + 1 >= data.length || (index + 1 < data.length && data[index + 1] != 'i'))) {
				sb.append(data[index++]);
				continue;
			}
			if (index == 0 && (data[index] == 'i' || (index + 1 < data.length
					&& ((data[index] == '-' || data[index] == '+') && data[index + 1] == 'i')))) {
				break;
			}
			throw new IllegalStateException("Invalid complex number.");
		}
		double re;
		if (sb.length() == 0) {
			re = 0;
		} else {
			re = Double.parseDouble(sb.toString());
		}

		int indexIm = index;
		// imaginary
		boolean imag = false;
		sb = new StringBuilder();
		while (true) {
			if (index >= data.length)
				break;
			if (Character.isDigit(data[index])) {
				sb.append(data[index++]);
				continue;
			}
			if (data[index] == 'i' && index == indexIm) {
				index++;
				imag = true;
				continue;
			}
			if (index + 1 < data.length && (data[index] == '-' || data[index] == '+') && data[index + 1] == 'i') {
				sb.append(data[index++]);

				index++;
				imag = true;
				continue;
			}
			throw new IllegalStateException("Invalid complex number.");
		}
		double im;
		if (sb.length() == 0) {
			if (imag) {
				im = 1;
			} else {
				im = 0;
			}
		} else if (sb.length() == 1 && sb.charAt(0) == '-') {
			im = -1;
		}else if (sb.length() == 1 && sb.charAt(0) == '+') {
			im = 1;
		}
		else {
			im = Double.parseDouble(sb.toString());
		}

		return new Complex(re, im);
	}

}
