package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class SubspaceExploreUtil defines three implementations of fill algorithm.
 * 
 * 
 * @author Filip
 */
public class SubspaceExploreUtil {

	/**
	 * This method is Breadth-First Search implementation of fill algorithm.
	 * 
	 * @param <S>
	 * @param s0         start state
	 * @param process    given process
	 * @param succ       given succ
	 * @param acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> zaIstraziti = new LinkedList<S>();
		zaIstraziti.add(s0.get());
		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.get(0);
			if (!acceptable.test(si)) {
				zaIstraziti.remove(0);
				continue;
			}
			process.accept(si);
			zaIstraziti.addAll(succ.apply(si));
			zaIstraziti.remove(0);
		}
	}

	/**
	 * This method is Depth-First Search implementation of fill algorithm.
	 * 
	 * @param <S>
	 * @param s0         start state
	 * @param process    given process
	 * @param succ       given succ
	 * @param acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> zaIstraziti = new LinkedList<S>();
		zaIstraziti.add(s0.get());
		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.get(0);
			if (!acceptable.test(si)) {
				zaIstraziti.remove(0);
				continue;
			}
			process.accept(si);
			zaIstraziti.remove(0);
			zaIstraziti.addAll(0, succ.apply(si));
		}
	}

	/**
	 * This method is optimized Breadth-First Search implementation of fill
	 * algorithm.
	 * 
	 * @param <S>
	 * @param s0         start state
	 * @param process    given process
	 * @param succ       given succ
	 * @param acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> zaIstraziti = new LinkedList<S>();
		Set<S> posjeceni = new HashSet<S>();
		posjeceni.add(s0.get());
		zaIstraziti.add(s0.get());
		while (!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.get(0);
			if (!acceptable.test(si)) {
				zaIstraziti.remove(0);
				continue;
			}
			process.accept(si);
			zaIstraziti.remove(0);
			for (S s : succ.apply(si)) {
				if (posjeceni.contains(s))
					continue;
				zaIstraziti.add(s);
			}
			posjeceni.addAll(zaIstraziti);

		}
	}

}
