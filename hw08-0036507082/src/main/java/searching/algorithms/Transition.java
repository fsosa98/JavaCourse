package searching.algorithms;

/**
 * Class Transition defines data structure and a set of methods for working with
 * it.
 * 
 * Users can get state and cost.
 * 
 * @author Filip
 * @param <S>
 */
public class Transition<S> {

	private S state;
	private double cost;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param state given state
	 * @param cost  given cost
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for state.
	 * 
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter for cost.
	 * 
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

}
