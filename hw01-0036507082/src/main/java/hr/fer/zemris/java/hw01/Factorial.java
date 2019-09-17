package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji se koristi za izračun faktorijela
 * prirodnog broja između 3 i 20.
 * 
 * @author Fiiliip
 * 
 * @version 1.0
 */
public class Factorial {
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args argumenti komandne linije. Ne koristimo ih.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String broj;
		System.out.print("Unesite broj > ");
		while (!(broj = scanner.next()).equals("kraj")) {
			try {
				long faktorijel = faktorijel(broj);
				System.out.println(broj + "! = " + faktorijel);
			}catch (IllegalArgumentException exception) {
				System.out.println(exception.getMessage());
			}
			System.out.print("Unesite broj > ");
		}
		System.out.println("Doviđenja.");
		scanner.close();
	}
	
	
	/**
	 * Metoda koja se koristi za izračun faktorijela.
	 * Argument može biti bilo koji cijeli broj,
	 * 
	 * @param ulaz broj za koji se izračunava vrijednost faktorijela.
	 * @return faktorijel broja.
	 * @throws IllegalArgumentException ako argument nije prirodni broj 
	 * između 3 i 20.
	 */
	public static long faktorijel(String ulaz) throws IllegalArgumentException{
		try {
			int broj = Integer.parseInt(ulaz);
			if (broj < 3 || broj > 20) {
				throw new IllegalArgumentException("\'" + ulaz + "\' nije broj u dozvoljenom rasponu.");
			}
			long faktorijel = 1;
			for (int i = 2; i <= broj; i++) {
				faktorijel *= i;
			}
			return faktorijel;
		}catch (NumberFormatException exception) {
			throw new IllegalArgumentException("\'" + ulaz + "\' nije cijeli broj.");
		}
	}
}
