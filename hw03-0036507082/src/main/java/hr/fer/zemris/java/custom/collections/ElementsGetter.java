package hr.fer.zemris.java.custom.collections;

/**
 * Interface ElementsGetter is an iterator over a collection.
 * 
 * Users can check if next element is available, get next element.
 * 
 * @author Filip
 */
public interface ElementsGetter {

	/**
	 * Returns true if the collection has more elements.
	 * 
	 * @return true if the collection has more elements false if the collection has
	 *         not more elements
	 */
	public boolean hasNextElement();

	/**
	 * Returns the next element in the collection.
	 * 
	 * @return next element
	 */
	public Object getNextElement();

	/**
	 * This method calls method from class processor for every element from
	 * collection.
	 * 
	 * @param p
	 */
	public default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
