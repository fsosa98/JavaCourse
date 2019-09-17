package hr.fer.zemris.java.custom.collections;

/**
 * Class Collection defines data structure and a set of 
 * methods for working with it.
 * 
 * Users can get size of collection, add elements, check if 
 * some element is in collection, remove element, check if 
 * collection is empty or clear collection.
 * 
 * @author Filip
 */
public interface Collection {
	
	/**
	 * This method checks if collection is empty.
	 * 
	 * @return  true if collection is empty
	 * 			false if collection is not empty
	 */
	public default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * This method calculates the size of collection.
	 * 
	 * @return number of currently stored objects in this collections.
	 */
	public int size();
	
	/**
	 * This method adds the given object into this collection. 
	 * 
	 * @param value
	 */
	public void add(Object value);
	
	/**
	 * This method checks if collection contains the given value.
	 * 
	 * @param value to check if it is in the collection
	 * @return true if value is in the collection
	 * 		   false if value is not in the collection
	 */
	public boolean contains(Object value);
	
	/**
	 * This method remove the given value if collection contains it.
	 * 
	 * @param value to be removed
	 * @return true if object with value is removed.
	 * 		   false if object with value is not removed.
	 */
	public boolean remove(Object value);
	
	/**
	 * This method allocates new array with size 
	 * equals to the size of this collections, 
	 * fills it with collection content and returns the array. 
	 * 
	 * @return Object array with elements of collection
	 */
	public Object[] toArray();
	
	/**
	 * This method calls {@link Processor#process(Object)}
	 * for each element of this collection. 
	 * 
	 * @param processor which method will be called.
	 */
	public default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * This method adds into the current collection 
	 * all elements from the given collection. 
	 * This other collection remains unchanged.
	 * 
	 * @param other collection whose elements will be copied
	 */
	public default void addAll(Collection other) {
		
		/**
		 * Class LocalClassProcessor represents processor 
		 * that is used for adding values to this collection.
		 *
		 */
		class LocalClassProcessor implements Processor{
			
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}
		
		other.forEach(new LocalClassProcessor());
		
	}
	
	/**
	 * This method removes all elements from this collection.
	 * 
	 */
	public void clear();
	
	/**
	 * This method create new ElementsGetter.
	 * 
	 * @return ElementsGetter
	 */
	public ElementsGetter createElementsGetter();
	
	/**
	 * This method adds into the current collection 
	 * all elements from the given collection that 
	 * satisfies test from tester. 
	 * This other collection remains unchanged.
	 * 
	 * @param col given collection
	 * @param tester given tester
	 */
	public default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			Object value = getter.getNextElement();
			if (tester.test(value)) {
				this.add(value);
			}
		}
	}
	
}
