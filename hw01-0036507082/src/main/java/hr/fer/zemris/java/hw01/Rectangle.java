package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji se koristi za izračun površine 
 * i opsega pravokutnika.
 * 
 * @author Fiiliip
 *
 * @version 1.0
 */
public class Rectangle {
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args argumenti komandne linije koji predstavljaju
	 * stranice pravokutnika(širina visina).
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				double a = Double.parseDouble(args[0]);
				double b = Double.parseDouble(args[1]);
				if (a <= 0 || b <= 0) {
					System.out.println("Argumenti trebaju biti pozitivni brojevi.");
				}else {
					double opseg = opseg(a, b);
					double povrsina = povrsina(a, b);
					System.out.println("Pravokutnik širine " + a + " i visine " + b
							+ " ima površinu " + povrsina + " te opseg " + opseg + ".");
				}
			}catch(Exception exception) {
				System.out.println("Argumenti trebaju biti pozitivni brojevi.");
			}
		}
		else if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);
			double a = unos("širinu", scanner);
			double b = unos("visinu", scanner);
			scanner.close();
			double opseg = opseg(a, b);
			double povrsina = povrsina(a, b);
			System.out.println("Pravokutnik širine " + a + " i visine " + b
					+ " ima površinu " + povrsina + " te opseg " + opseg + ".");
		}
		else {
			System.out.println("Broj argumenata različit je od 0 i različit je od 2.");
		}
	}
	
	/**
	 * Metoda koja se koristi za unos pozitivnih brojeva.
	 * 
	 * @param tekst koji se ispisuju pri unosu brojeva.
	 * @return broj veći od 0.
	 */
	public static double unos(String tekst, Scanner scanner) {
		double x;
		while (true) {
			System.out.print("Unesite " +  tekst + " > ");
			String unos = scanner.next();
			try {
				x = Double.parseDouble(unos);
				if (x < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}else if(x == 0) {
					System.out.println("Unesite pozitivnu vrijednost");
				}else {
					break;
				}
			}catch(Exception exception) {
				System.out.println("\'" + unos + "\'" + " se ne može protumačiti kao broj.");
			}
		}
		return x;
	}
	
	/**
	 * Metoda koja se koristi za izračun opsega.
	 * 
	 * @param a predstavlja širinu pravokutinka.
	 * @param b predstavlja visinu pravokutinka.
	 * @return opseg pravokutnika.
	 */
	public static double opseg(double a, double b) {
		return 2 * (a + b);
	}
	
	/**
	 * Metoda koja se koristi za izračun površine.
	 * 
	 * @param a predstavlja širinu pravokutinka.
	 * @param b predstavlja visinu pravokutinka.
	 * @return površina pravokutnika.
	 */
	public static double povrsina(double a, double b) {
		return a * b;
	}	
}
