package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Class SlagalicaMain is demo class.
 * 
 * @author Filip
 */
public class SlagalicaMain {

	/**
	 * This is main method.
	 * 
	 * @param args defines configuration
	 */
	public static void main(String[] args) {

//		if (args.length != 1 || args[0].length() != 9) {
//			System.out.println("Duljina argumenta mora biti 9.");
//			System.exit(1);
//		}
//
//		int pom[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		int polje[] = new int[9];
//
//		try {
//			for (int i = 0; i < args[0].length(); i++) {
//				int x = Character.getNumericValue(args[0].charAt(i));
//				polje[i] = x;
//				pom[x] = 1;
//			}
//		} catch (Exception exc) {
//			System.out.println("Krivi argument.");
//			System.exit(1);
//		}
//		for (int i = 0; i < pom.length; i++) {
//			if (pom[i] != 1) {
//				System.out.println("Krivi argument.");
//				System.exit(1);
//			}
//		}
//
//		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(polje));
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(new int[] { 2, 3, 0, 1, 4, 6, 7, 5, 8 }));

		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		SlagalicaViewer.display(rješenje);

	}

}
