package searching.slagalica;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Class Slagalica defines data structure and a set of methods for working with
 * it.
 * 
 * Users can get reference, test, apply and accept configuration.
 * 
 * @author Filip
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	private KonfiguracijaSlagalice reference;
	private final static int[] goal = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

	/**
	 * Constructor with given reference.
	 * 
	 * @param reference given reference
	 */
	public Slagalica(KonfiguracijaSlagalice reference) {
		this.reference = reference;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(t.getPolje(), goal);
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<Transition<KonfiguracijaSlagalice>>();

		int x = t.indexOfSpace();

		if (x % 3 != 0) {
			int[] pom = t.getPolje().clone();
			int tmp = pom[x];
			pom[x] = pom[x - 1];
			pom[x - 1] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(pom), 1));
		}
		if (x % 3 != 2) {
			int[] pom = t.getPolje().clone();
			int tmp = pom[x];
			pom[x] = pom[x + 1];
			pom[x + 1] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(pom), 1));
		}
		if (x != 0 && x != 1 && x != 2) {
			int[] pom = t.getPolje().clone();
			int tmp = pom[x];
			pom[x] = pom[x - 3];
			pom[x - 3] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(pom), 1));
		}
		if (x != 6 && x != 7 && x != 8) {
			int[] pom = t.getPolje().clone();
			int tmp = pom[x];
			pom[x] = pom[x + 3];
			pom[x + 3] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(pom), 1));
		}

		return list;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return reference;
	}

}
