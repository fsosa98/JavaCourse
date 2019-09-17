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
public class Collection {
	
	/**
	 * Default Constructor.
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * This method checks if collection is empty.
	 * 
	 * @return  true if collection is empty
	 * 			false if collection is not empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * This method calculates the size of collection.
	 * 
	 * @return number of currently stored objects in this collections.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * This method adds the given object into this collection. 
	 * 
	 * @param value
	 */
	public void add(Object value){
		
	}
	
	/**
	 * This method checks if collection contains the given value.
	 * 
	 * @param value to check if it is in the collection
	 * @return true if value is in the collection
	 * 		   false if value is not in the collection
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * This method remove the given value if collection contains it.
	 * 
	 * @param value to be removed
	 * @return true if object with value is removed.
	 * 		   false if object with value is not removed.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * This method allocates new array with size 
	 * equals to the size of this collections, 
	 * fills it with collection content and returns the array. 
	 * 
	 * @return Object array with elements of collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This method calls {@link Processor#process(Object)}
	 * for each element of this collection. 
	 * 
	 * @param processor which method will be called.
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * This method adds into the current collection 
	 * all elements from the given collection. 
	 * This other collection remains unchanged.
	 * 
	 * @param other collection whose elements will be copied
	 */
	public void addAll(Collection other) {
		
		/**
		 * Class LocalClassProcessor represents processor 
		 * that is used for adding values to this collection.
		 *
		 */
		class LocalClassProcessor extends Processor{
			
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
	public void clear() {
		
	}
	
}
