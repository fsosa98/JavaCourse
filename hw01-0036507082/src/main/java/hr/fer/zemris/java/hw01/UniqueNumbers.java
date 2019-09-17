package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji se koristi za rad 
 * sa sortiranim binarnim stablom.
 * 
 * @author Fiiliip
 * 
 * @version 1.0
 */
public class UniqueNumbers {
	
	/**
	 * Klasa predstavlja čvor binarnog stabla.
	 *
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	/**
	 * Metoda koja dodaje novi cvor u stablo.
	 * 
	 * @param glava predstavlja korijen stabla.
	 * @param val predstavlja vrijednost novog čvora.
	 * @return kroijen stabla.
	 */
	public static TreeNode addNode(TreeNode glava, int val) {
		if (glava == null) {
			TreeNode cvor = new TreeNode();
			cvor.value = val;
			return cvor;
		}else {
			if (glava.value == val) {
			}else if(glava.value < val) {
				glava.right = addNode(glava.right, val);
			}else {
				glava.left = addNode(glava.left, val);
			}
			return glava;
		}
	}
	
	/**
	 * Metoda koja izračunava veličinu stabla.
	 * 
	 * @param glava predstavlja korijen stabla.
	 * @return veličinu stabla.
	 */
	public static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}
		return 1 + treeSize(glava.left) + treeSize(glava.right);
	}
	
	/**
	 * Metoda provjerava sadrži li stablo cvor sa
	 * zadanom vrijednosti.
	 * 
	 * @param glava predstavlja korijen stabla.
	 * @param val vrijednost koja se provjerava.
	 * @return True ako sadrži, false inače.
	 */
	public static boolean containsValue(TreeNode glava, int val) {
		if (glava == null) {
			return false;
		}
		else {
			if (glava.value == val) {
				return true;
			}
			else {
				if (glava.value > val) {
					return containsValue(glava.left, val);
				}
				else {
					return containsValue(glava.right, val);
				}
			}
		}
	}
	
	/**
	 * Metoda ispisuje stablo sortirano uzlazno.
	 * 
	 * @param glava korijen stabla.
	 */
	public static void printMinToMax(TreeNode glava) {
		if (glava != null) {
			printMinToMax(glava.left);
			System.out.print(glava.value + " ");
			printMinToMax(glava.right);
		}
	}

	/**
	 * Metoda ispisuje stablo sortirano silazno.
	 * 
	 * @param glava korijen stabla.
	 */
	public static void printMaxToMin(TreeNode glava) {
		if (glava != null) {
			printMaxToMin(glava.right);
			System.out.print(glava.value + " ");
			printMaxToMin(glava.left);
		}
	}
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args argumenti komandne linije. Ne koristimo ih.
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		TreeNode glava = null;
		String ulaz;
		System.out.print("Unesite broj > ");
		while (!(ulaz = scanner.next()).equals("kraj")) {
			try {
				int broj = Integer.parseInt(ulaz);
				if (containsValue(glava, broj)) {
					System.out.println("Broj već postoji. Preskačem.");
				}else {
					glava = addNode(glava, broj);
					System.out.println("Dodano.");
				}
			}catch (IllegalArgumentException exception) {
				System.out.println("\'" + ulaz + "\' nije cijeli broj.");
			}
			System.out.print("Unesite broj > ");
		}
		printMinToMax(glava);
		System.out.println();
		printMaxToMin(glava);
		scanner.close();
		
	}
}
