package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class SearchUtil defines two implementations of searching algorithm.
 * 
 * 
 * @author Filip
 */
public class SearchUtil {

	/**
	 * This method is Breadth-First Search implementation of searching algorithm.
	 * 
	 * @param <S>
	 * @param s0   start state
	 * @param succ given succ
	 * @param goal final
	 * @return
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> zaIstraziti = new LinkedList<Node<S>>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));
		while (!zaIstraziti.isEmpty()) {

			Node<S> ni = zaIstraziti.get(0);
			if (goal.test(ni.getState()))
				return ni;

			for (Transition<S> transition : succ.apply(ni.getState())) {

				zaIstraziti.add(zaIstraziti.size(),
						new Node<S>(ni, transition.getState(), ni.getCost() + transition.getCost()));
			}
			zaIstraziti.remove(0);
		}
		return null;
	}

	/**
	 * This method is optimized Breadth-First Search implementation of fill
	 * algorithm.
	 * 
	 * @param <S>
	 * @param s0   start state
	 * @param succ given succ
	 * @param goal final
	 * @return
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> zaIstraziti = new LinkedList<Node<S>>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));

		Set<Node<S>> posjeceni = new HashSet<Node<S>>();
		posjeceni.add(new Node<S>(null, s0.get(), 0));

		while (!zaIstraziti.isEmpty()) {

			Node<S> ni = zaIstraziti.get(0);
			if (goal.test(ni.getState()))
				return ni;

			for (Transition<S> transition : succ.apply(ni.getState())) {
				Node<S> node = new Node<S>(ni, transition.getState(), ni.getCost() + transition.getCost());
				if (!posjeceni.contains(node)) {
					zaIstraziti.add(zaIstraziti.size(), node);
					posjeceni.add(node);
				}
			}
			zaIstraziti.remove(0);
		}
		return null;
	}

}
